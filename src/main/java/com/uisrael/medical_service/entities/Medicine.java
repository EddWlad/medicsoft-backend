package com.uisrael.medical_service.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    @EqualsAndHashCode.Include
    private UUID idMedicine;

    @Column(columnDefinition = "TEXT")
    private String photo;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 35)
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
