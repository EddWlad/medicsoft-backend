package com.uisrael.medical_service.dtos;


import com.uisrael.medical_service.entities.Medicine;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestockDTO {
    private UUID idRestock;
    private Date restockDate;
    private Medicine medicine;
    private Double quantity;
    private String observation;
    private Integer status;
}
