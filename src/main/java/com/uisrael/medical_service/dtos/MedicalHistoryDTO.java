package com.uisrael.medical_service.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uisrael.medical_service.entities.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedicalHistoryDTO {
    @EqualsAndHashCode.Include
    private UUID idMedicalHistory;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime birthDate;

    private Integer gender;

    private Float weight;

    private Float size;

    private Integer year;

    @Size(min = 3, max = 500)
    private String allergies;

    @Size(min = 3, max = 300)
    private String observation;

    private Integer status = 1;

    @JsonIgnore
    private User user;

}
