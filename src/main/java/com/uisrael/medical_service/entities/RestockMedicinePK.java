package com.uisrael.medical_service.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class RestockMedicinePK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_restock", foreignKey = @ForeignKey(name = "FK_RESTOCK_MEDICINE_R"))
    private Restock restock;

    @ManyToOne
    @JoinColumn(name = "id_medicine", foreignKey = @ForeignKey(name = "FK_RESTOCK_MEDICINE_M"))
    private Medicine medicine;

}
