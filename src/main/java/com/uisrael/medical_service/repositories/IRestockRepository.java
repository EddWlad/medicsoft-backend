package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Restock;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRestockRepository extends IGenericRepository<Restock, UUID> {

}
