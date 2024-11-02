package com.uisrael.medical_service.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name= "medicine")

public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String photo;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 100)
    private String description;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 100)
    private String unitType;

    @NotNull
    private Double price;

    @NotNull
    private Double stock = 0.0;

    @Column(nullable = false, columnDefinition = "Integer default 1")
    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    //@JsonManagedReference
    private List<Dispensary> dispensaries = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    //@ToString.Exclude
    //@JsonManagedReference
    private List<Restock> restocks = new ArrayList<>();

}
