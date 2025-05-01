package com.uisrael.medical_service.services;

import com.uisrael.medical_service.entities.Diagnostic;
import com.uisrael.medical_service.dtos.DiagnosticDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDiagnosticService extends IGenericService<Diagnostic, UUID> {

    Long countDiagnostic();
    String generateDiagnosticFromSymptoms(String symptoms);

    List<DiagnosticDTO> findDiagnosticsByPatientNameAndLastName(String name, String lastName);

    void markAllAsSeenByPatientId(UUID idPatient);

}
