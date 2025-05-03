package com.uisrael.medical_service.dtos;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedicineRestockDetailDTO {
    @EqualsAndHashCode.Include
    private UUID idMedicine;
    private String photo;
    private String name;
    private Integer quantity;
    private String description;
    private Double price;
}
