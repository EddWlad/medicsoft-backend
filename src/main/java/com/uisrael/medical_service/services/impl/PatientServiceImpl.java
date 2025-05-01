package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.MedicalHistory;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.repositories.IPatientRepository;
import com.uisrael.medical_service.services.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl extends GenericServiceImpl<MedicalHistory, UUID> implements IPatientService {

    private final IPatientRepository patientRepository;



    @Override
    public Long countPatient() {
        return patientRepository.count();
    }

    @Override
    protected IGenericRepository<MedicalHistory, UUID> getRepo() {
        return patientRepository;
    }
}
