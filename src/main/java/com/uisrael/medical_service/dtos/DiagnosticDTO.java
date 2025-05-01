package com.uisrael.medical_service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uisrael.medical_service.entities.MedicalHistory;
import com.uisrael.medical_service.entities.User;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DiagnosticDTO {

    @EqualsAndHashCode.Include
    private UUID idDiagnostic;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime diagnosticDate = LocalDateTime.now();

    @Size(min = 3, max = 500)
    private String symptoms;

    @Size(min = 3, max = 900)
    private String diagnostic;

    @Size(min = 3, max = 600)
    private String observation;

    private Integer status = 1;

    private boolean isNew = true;

    private User user;
}
