package com.uisrael.medical_service.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicineDTO {
    private Long id;
    private String photo;
    private String name;
    private String description;
    private String unitType;
    private Double price;
    private Double stock;
    private Integer status;
}
