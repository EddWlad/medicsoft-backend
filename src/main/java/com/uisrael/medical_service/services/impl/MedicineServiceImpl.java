package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.repositories.IMedicineRepository;
import com.uisrael.medical_service.services.IMedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl extends GenericServiceImpl<Medicine, UUID> implements IMedicineService {
    private final IMedicineRepository medicineRepository;


    @Override
    public Long countMedicine() {
        return medicineRepository.count();
    }

    @Override
    protected IGenericRepository<Medicine, UUID> getRepo() {
        return medicineRepository;
    }
}
