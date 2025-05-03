package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.dtos.*;
import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.entities.Restock;
import com.uisrael.medical_service.entities.Stock;
import com.uisrael.medical_service.repositories.*;
import com.uisrael.medical_service.services.IRestockService;
import com.uisrael.medical_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestockServiceImpl extends GenericServiceImpl<Restock, UUID> implements IRestockService {

    private final IRestockRepository restockRepository;
    private final IRestockMedicineRepository rmRepo;
    private final IStockRepository stockRepository;
    private final IMedicineRepository medicineRepository;
    private final MapperUtil mapperUtil;

    @Override
    protected IGenericRepository<Restock, UUID> getRepo() {
        return restockRepository;
    }

    @Override
    public Long countRestock() {
        return restockRepository.count();
    }

    @Transactional
    @Override
    public Restock saveTransactional(Restock restock, List<RestockDetailDTO> medicines) throws Exception {
        if (medicines == null || medicines.isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos una medicina para el reabastecimiento.");
        }

        restockRepository.save(restock);

        for (RestockDetailDTO dto : medicines) {
            UUID medId = dto.getIdMedicine();
            Integer quantity = dto.getQuantity();

            if (quantity == null || quantity <= 0) {
                throw new IllegalArgumentException("Cantidad inválida para la medicina con ID: " + medId);
            }

            Medicine medicine = medicineRepository.findById(medId)
                    .orElseThrow(() -> new IllegalArgumentException("Medicina no encontrada con ID: " + medId));

            // Actualizar el stock real
            Stock stock = medicine.getStock();
            if (stock == null) {
                throw new IllegalStateException("La medicina con ID " + medId + " no tiene stock asignado.");
            }

            stock.setQuantity(stock.getQuantity() + quantity);
            stock.setLastUpdate(LocalDateTime.now());
            stockRepository.save(stock);

            // Guardar en la tabla intermedia la cantidad específica
            rmRepo.saveMedicine(restock.getIdRestock(), medId, quantity);
        }

        return restock;
    }

    @Override
    public RestockDetailMedicineDTO findWithMedicines(UUID id) throws Exception {
        Restock restock = restockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restock no encontrado con ID: " + id));

        List<Object[]> result = rmRepo.findRestockDetails(id);

        List<MedicineRestockDetailDTO> medicines = result.stream().map(obj -> {
            MedicineRestockDetailDTO dto = new MedicineRestockDetailDTO();
            dto.setIdMedicine(UUID.fromString(obj[0].toString()));
            dto.setPhoto(obj[1] != null ? obj[1].toString() : null);
            dto.setName(obj[2].toString());
            dto.setQuantity(Integer.parseInt(obj[3].toString()));
            dto.setDescription(obj[4] != null ? obj[4].toString() : null);
            dto.setPrice(obj[5] != null ? Double.parseDouble(obj[5].toString()) : null);
            return dto;
        }).toList();

        RestockDTO restockDTO = mapperUtil.map(restock, RestockDTO.class);

        return new RestockDetailMedicineDTO(restockDTO, medicines);
    }

    @Override
    public List<RestockDetailMedicineDTO> findAllWithMedicines() throws Exception {
        List<Restock> restocks = super.findAll();
        List<RestockDetailMedicineDTO> responseList = new ArrayList<>();

        for (Restock restock : restocks) {
            List<Object[]> result = rmRepo.findRestockDetails(restock.getIdRestock());

            List<MedicineRestockDetailDTO> medicines = result.stream().map(obj -> {
                MedicineRestockDetailDTO dto = new MedicineRestockDetailDTO();
                dto.setIdMedicine(UUID.fromString(obj[0].toString()));
                dto.setPhoto(obj[1] != null ? obj[1].toString() : null);
                dto.setName(obj[2].toString());
                dto.setQuantity(Integer.parseInt(obj[3].toString()));
                dto.setDescription(obj[4] != null ? obj[4].toString() : null);
                dto.setPrice(obj[5] != null ? Double.parseDouble(obj[5].toString()) : null);
                return dto;
            }).toList();

            RestockDTO restockDTO = mapperUtil.map(restock, RestockDTO.class);
            responseList.add(new RestockDetailMedicineDTO(restockDTO, medicines));
        }

        return responseList;
    }

    @Override
    @Transactional
    public Restock updateWithMedicines(UUID id, Restock restock, List<RestockDetailDTO> details) throws Exception {
        Restock existing = restockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restock no encontrado con ID: " + id));

        // 1. Revertir stock anterior
        List<Object[]> oldDetails = rmRepo.findRestockDetails(id);
        for (Object[] row : oldDetails) {
            UUID medId = UUID.fromString(row[0].toString());
            Integer qty = Integer.parseInt(row[3].toString());

            Medicine medicine = medicineRepository.findById(medId)
                    .orElseThrow(() -> new IllegalArgumentException("Medicina no encontrada con ID: " + medId));

            Stock stock = medicine.getStock();
            if (stock == null) throw new IllegalStateException("Stock no definido para medicina");

            stock.setQuantity(stock.getQuantity() - qty);
            stock.setLastUpdate(LocalDateTime.now());
            stockRepository.save(stock);
        }

        // 2. Eliminar relaciones antiguas
        rmRepo.deleteByRestockId(id);

        // 3. Actualizar campos
        existing.setObservation(restock.getObservation());
        restockRepository.save(existing);

        // 4. Insertar nueva relación y sumar al stock
        for (RestockDetailDTO dto : details) {
            UUID medId = dto.getIdMedicine();
            Integer qty = dto.getQuantity();

            Medicine medicine = medicineRepository.findById(medId)
                    .orElseThrow(() -> new IllegalArgumentException("Medicina no encontrada con ID: " + medId));

            Stock stock = medicine.getStock();
            if (stock == null) throw new IllegalStateException("Stock no definido");

            stock.setQuantity(stock.getQuantity() + qty);
            stock.setLastUpdate(LocalDateTime.now());
            stockRepository.save(stock);

            rmRepo.saveMedicine(existing.getIdRestock(), medId, qty);
        }

        return existing;
    }

    @Override
    @Transactional
    public boolean softDeleteWithRollback(UUID id) throws Exception {
        Restock restock = restockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restock no encontrado con ID: " + id));

        if (restock.getStatus() == 0) {
            throw new IllegalStateException("Este restock ya ha sido eliminado.");
        }

        List<Object[]> oldDetails = rmRepo.findRestockDetails(id);
        for (Object[] row : oldDetails) {
            UUID medId = UUID.fromString(row[0].toString());
            int qty = Integer.parseInt(row[3].toString());

            Medicine med = medicineRepository.findById(medId)
                    .orElseThrow(() -> new IllegalArgumentException("Medicina no encontrada"));

            Stock stock = med.getStock();
            if (stock == null) {
                throw new IllegalStateException("No hay stock definido para la medicina con ID: " + medId);
            }

            if (stock.getQuantity() < qty) {
                throw new IllegalStateException("No se puede eliminar el restock porque causaría stock negativo para " + med.getName());
            }

            stock.setQuantity(stock.getQuantity() - qty);
            stock.setLastUpdate(LocalDateTime.now());
            stockRepository.save(stock);
        }

        // Eliminar relaciones físicas
        rmRepo.deleteByRestockId(id);

        // Soft delete del restock
        restock.setStatus(0);
        restockRepository.save(restock);

        return true;
    }

}

