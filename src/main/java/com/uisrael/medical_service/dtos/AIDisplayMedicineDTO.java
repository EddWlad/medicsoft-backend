package com.uisrael.medical_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIDisplayMedicineDTO {
    private String idMedicine;
    private String name;
    private String description;
    private String unitType;
    private Double price;
}
