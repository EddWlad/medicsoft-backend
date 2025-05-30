package com.uisrael.medical_service.dtos;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedicineDispensaryDetailDTO {
    @EqualsAndHashCode.Include
    private UUID idMedicine;
    private String photo;
    private String name;
    private String unitType;
    private String description;
    private Double price;
    private Integer quantity;
}
