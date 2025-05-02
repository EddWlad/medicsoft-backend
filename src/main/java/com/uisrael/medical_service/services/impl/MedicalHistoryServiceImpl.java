package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.MedicalHistory;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.repositories.IMedicalHistoryRepository;
import com.uisrael.medical_service.services.IMedicalHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalHistoryServiceImpl extends GenericServiceImpl<MedicalHistory, UUID> implements IMedicalHistoryService {

    private final IMedicalHistoryRepository patientRepository;



    @Override
    public Long countPatient() {
        return patientRepository.count();
    }

    @Override
    protected IGenericRepository<MedicalHistory, UUID> getRepo() {
        return patientRepository;
    }
}
