package com.uisrael.medical_service.services;


import com.uisrael.medical_service.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService {
    List<Patient> getAll();
    Optional<Patient> findById(Long id);
    Patient savePatient(Patient patient);
    Patient updatePatient(Long id, Patient patient);
    public boolean deletePatient(Long id);
    Long countPatient();
}
