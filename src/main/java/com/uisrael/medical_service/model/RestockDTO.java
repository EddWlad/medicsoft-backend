package com.uisrael.medical_service.model;


import com.uisrael.medical_service.entities.Medicine;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestockDTO {
    private Long id;
    private Date restockDate;
    private Medicine medicine;
    private Double quantity;
    private String observation;
    private Integer status;
}
