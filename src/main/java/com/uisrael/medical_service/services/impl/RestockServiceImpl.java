package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.entities.Restock;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.repositories.IMedicineRepository;
import com.uisrael.medical_service.repositories.IRestockRepository;
import com.uisrael.medical_service.services.IRestockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestockServiceImpl extends GenericServiceImpl<Restock, UUID> implements IRestockService {

    private final IRestockRepository restockRepository;
    private final IMedicineRepository medicineRepository;

    @Override
    protected IGenericRepository<Restock, UUID> getRepo() {
        return restockRepository;
    }

    @Override
    public Long countRestock() {
        return restockRepository.count();
    }

    /*@Override
    @Transactional
    public boolean restockMedicine(UUID id) {
        Restock restockDb = restockRepository.findById(id).orElse(null);
        if (restockDb != null) {
            medicineRepository.updateStock(restockDb.getMedicine().getIdMedicine(), restockDb.getQuantity());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean restockMedicine(UUID id, double previousQuantity) {
        Restock restockDb = restockRepository.findById(id).orElse(null);
        if (restockDb != null) {
            double quantityDifference = restockDb.getQuantity() - previousQuantity;
            medicineRepository.updateStock(restockDb.getMedicine().getIdMedicine(), quantityDifference);
            return true;
        } else {
            return false;
        }
    }*/


}

