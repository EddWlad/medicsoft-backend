package com.uisrael.medical_service.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateCreate = new Date();

    @NotBlank(message = "La identificación es requerida")
    @Size(min = 10, max = 13, message = "La identificación debe tener entre 10 y 13 dígitos")
    @Column(unique= true)
    private String identification;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "El correo electrónico no debe estar vacío")
    @Email(message = "Correo electrónico no válido")
    @Column(unique= true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    //@JsonBackReference
    private Role role;

    @Column(nullable = false, columnDefinition = "Integer default 1")
    private Integer status;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    //@JsonManagedReference
    private List<Dispensary> dispensaries = new ArrayList<>();
}
