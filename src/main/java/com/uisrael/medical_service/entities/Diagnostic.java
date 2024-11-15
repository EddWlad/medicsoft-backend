package com.uisrael.medical_service.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name= "diagnostic")

public class Diagnostic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date diagnosticDate;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 500)
    private String symptoms;

    @Size(min = 3, max = 900)
    private String diagnostic;

    @Size(min = 3, max = 600)
    private String observation;

    @Column(nullable = false, columnDefinition = "Integer default 1")
    private Integer status;

    private Boolean isNew;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "patient_id", nullable = true)
    //@JsonBackReference
    private Patient patient;
}
