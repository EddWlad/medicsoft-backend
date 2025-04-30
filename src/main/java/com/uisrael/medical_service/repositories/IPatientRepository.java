package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Patient;

import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface IPatientRepository extends IGenericRepository<Patient, UUID> {

}
