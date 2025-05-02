package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.MedicalHistory;

import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface IMedicalHistoryRepository extends IGenericRepository<MedicalHistory, UUID> {

}
