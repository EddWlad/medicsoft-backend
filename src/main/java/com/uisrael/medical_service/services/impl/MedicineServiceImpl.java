package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.dtos.MedicineDTO;
import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.repositories.IMedicineRepository;
import com.uisrael.medical_service.services.IMedicineService;
import com.uisrael.medical_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MedicineServiceImpl extends GenericServiceImpl<Medicine, UUID> implements IMedicineService{
    private final IMedicineRepository medicineRepository;
    private final MapperUtil mapperUtil;

    @Override
    public Long countMedicine() {
        return medicineRepository.count();
    }

    @Override
    protected IGenericRepository<Medicine, UUID> getRepo() {
        return medicineRepository;
    }



    @Transactional
    public Medicine create(MedicineDTO dto) throws Exception {
        String name = dto.getName();

        Optional<Medicine> activeMedicineOpt = medicineRepository.findByNameStatus(name);
        if (activeMedicineOpt.isPresent()) {
            throw new Exception("Medicine already exist.");
        }

        Optional<Medicine> deletedMedicineOpt = medicineRepository.findDeletedMedicine(name);
        if (deletedMedicineOpt.isPresent()) {
            Medicine existingMedicine = deletedMedicineOpt.get();
            existingMedicine.setStatus(1);
            existingMedicine.setStock(dto.getStock());
            existingMedicine.getStock().setStatus(1);
            return medicineRepository.save(existingMedicine);
        }

        // Crear nueva medicina
        Medicine medicine = mapperUtil.map(dto, Medicine.class);
        medicine.setStatus(1);
        medicine.getStock().setStatus(1);
        return medicineRepository.save(medicine);
    }

    @Override
    public boolean softDelete(UUID id) throws Exception {
        Optional<Medicine> optionalMedicine = medicineRepository.findById(id);

        if (optionalMedicine.isEmpty()) {
            return false;
        }

        Medicine medicine = optionalMedicine.get();
        medicine.setStatus(0);

        if (medicine.getStock() != null) {
            medicine.getStock().setStatus(0);
        }

        medicineRepository.save(medicine);
        return true;
    }
}
