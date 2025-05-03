package com.uisrael.medical_service.services;

import com.uisrael.medical_service.entities.Stock;

import java.util.UUID;

public interface IStockService extends IGenericService<Stock, UUID>{
    Long countStock();
}
