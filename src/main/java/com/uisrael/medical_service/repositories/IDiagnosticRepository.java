package com.uisrael.medical_service.repositories;

import com.uisrael.medical_service.entities.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiagnosticRepository extends JpaRepository<Diagnostic,Long> {
    List<Diagnostic> findByStatusNot(Integer status);
    @Query("SELECT d FROM Diagnostic d WHERE d.patient.name = :name AND d.patient.lastName = :lastName AND d.status = 1")
    List<Diagnostic> findByPatientNameAndLastName(@Param("name") String name, @Param("lastName") String lastName);

    List<Diagnostic> findByPatientIdAndIsNew(Long patientId, boolean isNew);

    List<Diagnostic> findByPatientIdAndIsNewTrue(Long patientId);
}
