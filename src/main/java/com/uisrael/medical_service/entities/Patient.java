package com.uisrael.medical_service.entities;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    @EqualsAndHashCode.Include
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

    @JsonIgnore
    @OneToMany(mappedBy = "patient",cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Dispensary> dispensaries = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "patient",cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Diagnostic> diagnostics = new ArrayList<>();
}
