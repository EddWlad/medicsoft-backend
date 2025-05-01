package com.uisrael.medical_service.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class DispensaryMedicinePK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_dispensary", foreignKey = @ForeignKey(name = "FK_DISPENSARY_MEDICINE_D"))
    private Dispensary dispensary;

    @ManyToOne
    @JoinColumn(name = "id_medicine", foreignKey = @ForeignKey(name = "FK_DIPENSARY_MEDICINE_M"))
    private Medicine medicine;
}
