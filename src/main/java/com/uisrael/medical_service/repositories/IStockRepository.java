package com.uisrael.medical_service.repositories;

import com.uisrael.medical_service.entities.Stock;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IStockRepository extends IGenericRepository<Stock, UUID>{

}
