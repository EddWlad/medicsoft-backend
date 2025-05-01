package com.uisrael.medical_service.services;


import com.uisrael.medical_service.entities.Restock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRestockService extends IGenericService<Restock, UUID> {

    Long countRestock();

    public boolean restockMedicine(UUID id);
    public boolean restockMedicine(UUID id, double previousQuantity);
}
