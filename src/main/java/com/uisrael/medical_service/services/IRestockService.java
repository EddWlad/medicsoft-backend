package com.uisrael.medical_service.services;


import com.uisrael.medical_service.entities.Restock;

import java.util.List;
import java.util.Optional;

public interface IRestockService {
    List<Restock> getAll();
    Optional<Restock> findById(Long id);
    Restock saveRestock(Restock restock);
    Restock updateRestock(Long id, Restock restock);
    public boolean deleteRestock(Long id);
    Long countRestock();

    public boolean restockMedicine(Long id);
    public boolean restockMedicine(Long id, double previousQuantity);
}
