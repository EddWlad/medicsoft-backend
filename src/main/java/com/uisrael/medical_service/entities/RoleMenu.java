package com.uisrael.medical_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RoleMenuPK.class)
public class RoleMenu {

    @Id
    private Role role;

    @Id
    private Menu menu;

}
