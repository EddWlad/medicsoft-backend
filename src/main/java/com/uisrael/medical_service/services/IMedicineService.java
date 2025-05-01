package com.uisrael.medical_service.services;

import com.uisrael.medical_service.entities.Medicine;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMedicineService extends IGenericService<Medicine, UUID>{
    Long countMedicine();
}
