package com.uisrael.medical_service.dtos;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedicineDTO {
    @EqualsAndHashCode.Include
    private UUID idMedicine;

    @Size(min = 3, max = 400)
    private String photo;

    @Size(min = 3, max = 35)
    private String name;

    @Size(min = 3, max = 100)
    private String description;

    @Size(min = 3, max = 100)
    private String unitType;

    private Double price;
    private Double stock = 0.0;
    private Integer status = 1;
}
