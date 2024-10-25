package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientRepository extends JpaRepository<Patient,Long> {
    List<Patient> findByStatusNot(Integer status);
}
