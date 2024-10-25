package com.uisrael.medical_service.service;

import com.uisrael.medical_service.entities.Diagnostic;

import java.util.List;
import java.util.Optional;

public interface IDiagnosticService {
    List<Diagnostic> getAll();
    Optional <Diagnostic> findById(Long id);
    Diagnostic saveDiagnostic(Diagnostic diagnostic);
    Diagnostic updateDiagnostic(Long id, Diagnostic diagnostic);
    public boolean deleteDiagnostic(Long id);
    Long countDiagnostic();
    String generateDiagnosticFromSymptoms(String symptoms);
}
