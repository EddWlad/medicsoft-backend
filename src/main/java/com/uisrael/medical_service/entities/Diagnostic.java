package com.uisrael.medical_service.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name= "diagnostic")
public class Diagnostic {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    @EqualsAndHashCode.Include
    private UUID idDiagnostic;

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
    private Patient patient;
}
