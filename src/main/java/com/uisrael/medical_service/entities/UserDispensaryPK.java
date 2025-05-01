package com.uisrael.medical_service.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class UserDispensaryPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "FK_USER_DISPENSARY_U"))
    private User user;

    @ManyToOne
    @JoinColumn(name ="id_dispensary", foreignKey = @ForeignKey(name = "FK_USER_DISPENSARY_D"))
    private Dispensary dispensary;
}
