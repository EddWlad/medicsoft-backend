package com.uisrael.medical_service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.entities.MedicalHistory;
import com.uisrael.medical_service.entities.User;

import jakarta.validation.constraints.NotNull;
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
public class DispensaryDTO {
    @EqualsAndHashCode.Include
    private UUID idDispensary;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dispensaryCreate = LocalDateTime.now();

    @NotNull(message = "Debe elegir el doctor por favor")
    @JsonIgnoreProperties({"medicalHistory"})
    private User doctor;

    @NotNull(message = "Debe elegir el paciente")
    @JsonIgnoreProperties({"medicalHistory"})
    private User patient;

    @Size(min = 3, max = 300)
    private String observation;

    private Integer status = 1;
}
