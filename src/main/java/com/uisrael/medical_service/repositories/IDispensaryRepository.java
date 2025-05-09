package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Dispensary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IDispensaryRepository extends IGenericRepository<Dispensary, UUID> {

}
