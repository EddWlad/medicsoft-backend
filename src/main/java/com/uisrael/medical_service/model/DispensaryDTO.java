package com.uisrael.medical_service.model;

import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.entities.Patient;
import com.uisrael.medical_service.entities.User;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DispensaryDTO {
    private Long id;
    private Date dispensayDate;
    private Double quantity;
    private Patient patient;
    private Medicine medicine;
    private User user;
    private String observation;
    private Integer status;
}
