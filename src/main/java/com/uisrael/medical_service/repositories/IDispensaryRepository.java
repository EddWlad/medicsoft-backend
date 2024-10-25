package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Dispensary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDispensaryRepository extends JpaRepository<Dispensary, Long> {
    List<Dispensary> findByStatusNot(Integer status);
}
