package com.uisrael.medical_service.services;

import com.uisrael.medical_service.dtos.MedicineDTO;
import com.uisrael.medical_service.entities.Medicine;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMedicineService extends IGenericService<Medicine, UUID>{
    Long countMedicine();

    Medicine create(MedicineDTO dto) throws Exception;

    boolean softDelete(UUID id) throws Exception;
}
