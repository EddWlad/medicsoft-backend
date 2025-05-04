package com.uisrael.medical_service.dtos;

import com.uisrael.medical_service.entities.Medicine;

import java.util.List;

public record AIDiagnosticDTO (
        String diagnostic,
        List<AIDisplayMedicineDTO> medicine
) { }
