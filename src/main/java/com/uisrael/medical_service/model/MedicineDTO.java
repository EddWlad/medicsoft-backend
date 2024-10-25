package com.uisrael.medical_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uisrael.medical_service.entities.Dispensary;
import com.uisrael.medical_service.entities.Restock;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
