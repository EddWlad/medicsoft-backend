package com.uisrael.medical_service.services.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.uisrael.medical_service.dtos.DispensaryDTO;
import com.uisrael.medical_service.dtos.DispensaryDetailDTO;
import com.uisrael.medical_service.dtos.DispensaryDetailMedicineDTO;
import com.uisrael.medical_service.dtos.MedicineDispensaryDetailDTO;
import com.uisrael.medical_service.entities.Dispensary;
import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.entities.Stock;
import com.uisrael.medical_service.repositories.*;
import com.uisrael.medical_service.services.IDispensaryService;
import com.uisrael.medical_service.utils.MapperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DispensaryServiceImpl extends GenericServiceImpl<Dispensary, UUID> implements IDispensaryService {

    private final IDispensaryRepository dispensaryRepository;
    private final IDispensaryMedicineRepository dmRepo;
    private final IMedicineRepository medicineRepository;
    private final IStockRepository stockRepository;
    private final MapperUtil mapperUtil;


    @Override
    protected IGenericRepository<Dispensary, UUID> getRepo() {
        return dispensaryRepository;
    }

    @Transactional
    @Override
    public Dispensary saveTransactional(Dispensary dispensary, List<DispensaryDetailDTO> medicines) throws Exception {
        if (medicines == null || medicines.isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos una medicina para la dispensación.");
        }

        dispensary.setDispensaryCreate(LocalDateTime.now());
        dispensaryRepository.save(dispensary);

        for (DispensaryDetailDTO dto : medicines) {
            UUID medId = dto.getIdMedicine();
            Integer cantidad = dto.getQuantity();

            if (cantidad == null || cantidad <= 0) {
                throw new IllegalArgumentException("Cantidad inválida para la medicina con ID: " + medId);
            }

            Medicine medicine = medicineRepository.findById(medId)
                    .orElseThrow(() -> new IllegalArgumentException("Medicina no encontrada con ID: " + medId));

            Stock stock = medicine.getStock();
            if (stock == null) {
                throw new IllegalStateException("La medicina con ID " + medId + " no tiene stock asociado.");
            }

            if (stock.getQuantity() < cantidad) {
                throw new IllegalArgumentException("Stock insuficiente para medicina con ID: " + medId);
            }

            stock.setQuantity(stock.getQuantity() - cantidad);
            stock.setLastUpdate(LocalDateTime.now());
            stockRepository.save(stock);

            dmRepo.saveMedicine(dispensary.getIdDispensary(), medId, cantidad);
        }

        return dispensary;
    }

    @Override
    public DispensaryDetailMedicineDTO findWithMedicines(UUID id) throws Exception {
        Dispensary dispensary = dispensaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dispensación no encontrada con ID: " + id));

        List<Object[]> result = dmRepo.findDispensaryDetails(id);
        List<MedicineDispensaryDetailDTO> medicines = result.stream().map(obj -> {
            MedicineDispensaryDetailDTO dto = new MedicineDispensaryDetailDTO();
            dto.setIdMedicine(UUID.fromString(obj[0].toString()));
            dto.setPhoto(obj[1] != null ? obj[1].toString() : null);
            dto.setName(obj[2].toString());
            dto.setQuantity(Integer.parseInt(obj[3].toString()));
            dto.setDescription(obj[4] != null ? obj[4].toString() : null);
            dto.setPrice(obj[5] != null ? Double.parseDouble(obj[5].toString()) : null);
            return dto;
        }).toList();

        DispensaryDTO dispensaryDTO = mapperUtil.map(dispensary, DispensaryDTO.class);
        return new DispensaryDetailMedicineDTO(dispensaryDTO, medicines);
    }

    @Override
    public List<DispensaryDetailMedicineDTO> findAllWithMedicines() throws Exception {
        List<Dispensary> dispensaries = super.findAll(); // Solo status ≠ 0
        List<DispensaryDetailMedicineDTO> responseList = new ArrayList<>();

        for (Dispensary dispensary : dispensaries) {
            List<Object[]> result = dmRepo.findDispensaryDetails(dispensary.getIdDispensary());
            List<MedicineDispensaryDetailDTO> medicines = result.stream().map(obj -> {
                MedicineDispensaryDetailDTO dto = new MedicineDispensaryDetailDTO();
                dto.setIdMedicine(UUID.fromString(obj[0].toString()));
                dto.setPhoto(obj[1] != null ? obj[1].toString() : null);
                dto.setName(obj[2].toString());
                dto.setQuantity(Integer.parseInt(obj[3].toString()));
                dto.setDescription(obj[4] != null ? obj[4].toString() : null);
                dto.setPrice(obj[5] != null ? Double.parseDouble(obj[5].toString()) : null);
                return dto;
            }).toList();

            DispensaryDTO dispensaryDTO = mapperUtil.map(dispensary, DispensaryDTO.class);
            responseList.add(new DispensaryDetailMedicineDTO(dispensaryDTO, medicines));
        }

        return responseList;
    }

    @Transactional
    @Override
    public Dispensary updateTransactional(UUID id, Dispensary updatedDispensary, List<DispensaryDetailDTO> medicines) throws Exception {
        Dispensary dispensary = dispensaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dispensación no encontrada con ID: " + id));

        // Revertir stock de las medicinas anteriores
        List<Object[]> oldDetails = dmRepo.findDispensaryDetails(id);
        for (Object[] obj : oldDetails) {
            UUID medId = UUID.fromString(obj[0].toString());
            int cantidadAnterior = Integer.parseInt(obj[3].toString());

            Medicine med = medicineRepository.findById(medId)
                    .orElseThrow(() -> new IllegalArgumentException("Medicina no encontrada para revertir stock"));
            Stock stock = med.getStock();
            if (stock != null) {
                stock.setQuantity(stock.getQuantity() + cantidadAnterior);
                stock.setLastUpdate(LocalDateTime.now());
                stockRepository.save(stock);
            }
        }

        // Eliminar relaciones antiguas
        dmRepo.deleteByDispensaryId(id);

        // Aplicar cambios nuevos
        dispensary.setObservation(updatedDispensary.getObservation());
        dispensaryRepository.save(dispensary);

        for (DispensaryDetailDTO dto : medicines) {
            UUID medId = dto.getIdMedicine();
            Integer cantidad = dto.getQuantity();

            Medicine medicine = medicineRepository.findById(medId)
                    .orElseThrow(() -> new IllegalArgumentException("Medicina no encontrada"));

            Stock stock = medicine.getStock();
            if (stock == null || stock.getQuantity() < cantidad) {
                throw new IllegalArgumentException("Stock insuficiente para actualizar la medicina: " + medId);
            }

            stock.setQuantity(stock.getQuantity() - cantidad);
            stock.setLastUpdate(LocalDateTime.now());
            stockRepository.save(stock);

            dmRepo.saveMedicine(id, medId, cantidad);
        }

        return dispensary;
    }

    @Transactional
    @Override
    public boolean softDeleteWithRollback(UUID id) throws Exception {
        Dispensary dispensary = dispensaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dispensación no encontrada"));

        // Revertir stock de las medicinas dispensadas
        List<Object[]> dispensed = dmRepo.findDispensaryDetails(id);
        for (Object[] obj : dispensed) {
            UUID medId = UUID.fromString(obj[0].toString());
            int quantity = Integer.parseInt(obj[3].toString());

            Medicine med = medicineRepository.findById(medId)
                    .orElseThrow(() -> new IllegalArgumentException("Medicina no encontrada"));
            Stock stock = med.getStock();
            if (stock != null) {
                stock.setQuantity(stock.getQuantity() + quantity);
                stock.setLastUpdate(LocalDateTime.now());
                stockRepository.save(stock);
            }
        }

        dispensary.setStatus(0);
        dispensaryRepository.save(dispensary);
        return true;
    }

    @Override
    public byte[] generatePdf(UUID id) throws Exception {
        DispensaryDetailMedicineDTO dto = this.findWithMedicines(id); // este ya lo tienes

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

        document.add(new Paragraph("Ticket de Dispensación", font));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Paciente: " + dto.getDispensaryDTO().getPatient().getName() + " " + dto.getDispensaryDTO().getPatient().getLastName()));
        document.add(new Paragraph("Cédula: " + dto.getDispensaryDTO().getPatient().getIdentification()));
        document.add(new Paragraph("Fecha: " + dto.getDispensaryDTO().getDispensaryCreate()));
        document.add(new Paragraph("Doctora: " + dto.getDispensaryDTO().getDoctor().getName() + " " + dto.getDispensaryDTO().getDoctor().getLastName()));
        document.add(new Paragraph("Observación: " + dto.getDispensaryDTO().getObservation()));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Medicamentos:", font));
        for (MedicineDispensaryDetailDTO med : dto.getMedicines()) {
            document.add(new Paragraph("- " + med.getName() + " | Cantidad: " + med.getQuantity() + " | Unidad: " + med.getUnitType()));
        }

        document.close();
        return out.toByteArray();
    }


}
