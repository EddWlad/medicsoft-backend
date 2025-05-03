package com.uisrael.medical_service.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    @EqualsAndHashCode.Include
    private UUID idDispensary;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dispensaryCreate = LocalDateTime.now();

    private String observation;

    @Column(nullable = false)
    private Integer status = 1;

    @ManyToOne
    @JoinColumn(name = "id_doctor", foreignKey = @ForeignKey(name = "FK_USER_DOCTOR"))
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "id_patient", foreignKey = @ForeignKey(name = "FK_USER_PATIENT"))
    private User patient;
}
