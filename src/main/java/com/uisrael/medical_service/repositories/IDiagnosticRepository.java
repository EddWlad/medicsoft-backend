package com.uisrael.medical_service.repositories;

import com.uisrael.medical_service.entities.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiagnosticRepository extends JpaRepository<Diagnostic,Long> {
    List<Diagnostic> findByStatusNot(Integer status);
}
