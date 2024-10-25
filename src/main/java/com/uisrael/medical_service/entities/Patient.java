package com.uisrael.medical_service.entities;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name= "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String department;


    @NotNull
    private Integer gender;


    @NotNull
    private Float weight;


    @NotNull
    private Float size;


    @NotNull
    private Integer year;

    @Size(min = 3, max = 300)
    private String observation;


    @NotNull
    @Column(columnDefinition = "Integer default 1")
    private Integer status;

    @OneToMany(mappedBy = "patient",cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    //@JsonManagedReference
    private List<Dispensary> dispensaries = new ArrayList<>();

    @OneToMany(mappedBy = "patient",cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    //@JsonManagedReference
    private List<Diagnostic> diagnostics = new ArrayList<>();
}
