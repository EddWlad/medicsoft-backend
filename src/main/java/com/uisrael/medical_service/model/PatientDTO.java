package com.uisrael.medical_service.model;

import com.uisrael.medical_service.entities.Diagnostic;
import com.uisrael.medical_service.entities.Dispensary;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {
    private Long id;
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
