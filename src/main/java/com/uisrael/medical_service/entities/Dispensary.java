package com.uisrael.medical_service.entities;


import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name= "dispensary")
public class Dispensary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dispensayDate;

    @NotNull
    private Double quantity;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "patient_id", nullable = false)
    //@JsonBackReference
    private Patient patient;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "medicine_id", nullable = false)
    //@JsonBackReference
    private Medicine medicine;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "user_id", nullable = false)
    //@JsonBackReference
    private User user;

    @Size(min = 3, max = 300)
    private String observation;

    @Column(nullable = false, columnDefinition = "Integer default 1")
    private Integer status;
}
