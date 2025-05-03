package com.uisrael.medical_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(DispensaryMedicinePK.class)
public class DispensaryMedicine {
    @Id
    private Dispensary dispensary;

    @Id
    private Medicine medicine;

    private Integer quantity;

    @Column(nullable = false)
    private Integer status = 1;
}
