package com.uisrael.medical_service.model;

import com.uisrael.medical_service.entities.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DiagnosticDTO {

    private Long id;
    private Date diagnosticDate;
    private String symptoms;
    private String diagnostic;
    private String observation;
    private Integer status;
    private Patient patient;
}
