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
@IdClass(UserDispensaryPK.class)
public class UserDispensary {
    @Id
    private User user;

    @Id
    private Dispensary dispensary;

    @Column(nullable = false)
    private Integer status = 1;
}
