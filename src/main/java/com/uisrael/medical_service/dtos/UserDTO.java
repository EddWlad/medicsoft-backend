package com.uisrael.medical_service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uisrael.medical_service.entities.MedicalHistory;
import com.uisrael.medical_service.entities.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {

    @EqualsAndHashCode.Include
    private UUID idUser;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreate = LocalDateTime.now();

    @Size(min = 10, max = 13, message = "La identificación debe tener entre 10 y 13 dígitos")
    private String identification;

    @Size(min = 3, max = 50)
    private String name;


    @Size(min = 3, max = 50)
    private String lastName;

    @Column(nullable = false, length = 60, unique = true)
    private String username;

    @NotBlank(message = "El correo electrónico no debe estar vacío")
    @Email(message = "Correo electrónico no válido")
    @Column(unique= true)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;


    private boolean enabled;

    private Integer status = 1;

    private MedicalHistory medicalHistory;

}
