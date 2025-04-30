package com.uisrael.medical_service.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "restock")
public class Restock {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    @EqualsAndHashCode.Include
    private UUID idRestock;

    @Temporal(TemporalType.DATE)
    private Date restockDate;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "medicine_id", nullable = false)
    //@JsonBackReference
    private Medicine medicine;

    @NotNull
    private Double quantity;

    @Size(min = 3, max = 300)
    private String observation;

    @Column(nullable = false, columnDefinition = "Integer default 1")
    private Integer status;
}
