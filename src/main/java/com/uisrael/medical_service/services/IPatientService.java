package com.uisrael.medical_service.services;


import com.uisrael.medical_service.entities.MedicalHistory;

import java.util.UUID;

public interface IPatientService extends IGenericService<MedicalHistory, UUID>{

    Long countPatient();
}
