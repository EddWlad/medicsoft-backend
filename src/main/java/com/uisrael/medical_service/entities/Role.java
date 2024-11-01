package com.uisrael.medical_service.entities;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name= "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "role",cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    //@ToString.Exclude
    //@JsonManagedReference
    private List<User> users = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "Integer default 1")
    private Integer status;
}
