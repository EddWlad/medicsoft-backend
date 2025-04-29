package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.repositories.IMedicineRepository;
import com.uisrael.medical_service.services.IMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements IMedicineService {
    @Autowired
    private IMedicineRepository medicineRepository;
    @Override
    public List<Medicine> getAll() {
        return medicineRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Medicine> findById(Long id) {
        return medicineRepository.findById(id);
    }

    @Override
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine updateMedicine(Long id, Medicine medicine) {
        Medicine medicineDb = medicineRepository.findById(id).orElse(null);
        if(medicine != null)
        {
            medicineDb.setName(medicine.getName());
            medicineDb.setDescription(medicine.getDescription());
            medicineDb.setPhoto(medicine.getPhoto());
            medicineDb.setStock(medicine.getStock());
            medicineDb.setUnitType(medicine.getUnitType());
            medicineDb.setPrice(medicine.getPrice());
            medicineDb.setStatus((medicine.getStatus()));
            return medicineRepository.save(medicineDb);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean deleteMedicine(Long id) {
        Medicine medicineDb = medicineRepository.findById(id).orElse(null);
        if(medicineDb != null)
        {
            medicineDb.setStatus(0);
            medicineRepository.save(medicineDb);
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public Long countMedicine() {
        return medicineRepository.count();
    }
}
