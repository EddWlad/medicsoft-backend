package com.uisrael.medical_service.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    @EqualsAndHashCode.Include
    private UUID idMedicine;

    @Column(nullable = true)
    private String photo;

    @Column(nullable = false)
    @Size(min = 3, max = 35)
    private String name;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String description;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String unitType;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double stock = 0.0;

    @Column(nullable = false, columnDefinition = "Integer default 1")
    private Integer status;


}
