package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Restock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRestockRepository extends JpaRepository<Restock,Long> {
    List<Restock> findByStatusNot(Integer status);
}
