package com.uisrael.medical_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {
    private UUID idPatient;
    private String name;
    private String lastName;
    private String department;
    private Integer gender;
    private Float weight;
    private Float size;
    private Integer year;
    private String observation;
    private Integer status;

}
