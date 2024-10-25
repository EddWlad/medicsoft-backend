package com.uisrael.medical_service.service;

import com.uisrael.medical_service.entities.Medicine;

import java.util.List;
import java.util.Optional;

public interface IMedicineService {
    List<Medicine> getAll();
    Optional<Medicine> findById(Long id);
    Medicine saveMedicine(Medicine medicine);
    Medicine updateMedicine(Long id, Medicine medicine);
    public boolean deleteMedicine(Long id);
    Long countMedicine();
}
