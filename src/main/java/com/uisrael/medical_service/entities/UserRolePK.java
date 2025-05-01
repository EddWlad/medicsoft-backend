package com.uisrael.medical_service.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class UserRolePK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "FK_USER_ROLE_U"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_role", foreignKey = @ForeignKey(name = "FK_USER_ROLE_R"))
    private Role role;
}
