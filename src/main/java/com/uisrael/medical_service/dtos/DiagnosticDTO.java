package com.uisrael.medical_service.dtos;

import com.uisrael.medical_service.entities.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DiagnosticDTO {

    private UUID idDiagnostic;
    private Date diagnosticDate;
    private String symptoms;
    private String diagnostic;
    private String observation;
    private Integer status;
    private Patient patient;
    private boolean isNew = true;
}
