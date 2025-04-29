package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.entities.Restock;
import com.uisrael.medical_service.repositories.IMedicineRepository;
import com.uisrael.medical_service.repositories.IRestockRepository;
import com.uisrael.medical_service.services.IRestockService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestockServiceImpl implements IRestockService {
    @Autowired
    private IRestockRepository restockRepository;
    @Autowired
    private IMedicineRepository medicineRepository;

    @Override
    public List<Restock> getAll() {
        return restockRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Restock> findById(Long id) {
        return restockRepository.findById(id);
    }

    @Override
    public Restock saveRestock(Restock restock) {
        return restockRepository.save(restock);
    }

    @Override
    public Restock updateRestock(Long id, Restock restock) {
        Restock restockDb = restockRepository.findById(id).orElse(null);
        if(restock != null)
        {
            restockDb.setRestockDate(restock.getRestockDate());
            restockDb.setMedicine(restock.getMedicine());
            restockDb.setQuantity(restock.getQuantity());
            restockDb.setStatus(restock.getStatus());
            restockDb.setObservation(restock.getObservation());
            return restockRepository.save(restockDb);
        }else{
            return null;
        }

    }

    @Override
    public boolean deleteRestock(Long id) {
        Restock restockDb = restockRepository.findById(id).orElse(null);
        if (restockDb != null && restockDb.getStatus() != 0) {
            Medicine medicineDb = restockDb.getMedicine();
            double newStock = medicineDb.getStock() - restockDb.getQuantity();
            medicineDb.setStock(newStock >= 0 ? newStock : 0);
            medicineRepository.save(medicineDb);


            restockDb.setStatus(0);
            restockRepository.save(restockDb);
            return true;
        }
        return false;
    }

    @Override
    public Long countRestock() {
        return restockRepository.count();
    }

    @Override
    @Transactional
    public boolean restockMedicine(Long id) {
        Restock restockDb = restockRepository.findById(id).orElse(null);
        if (restockDb != null) {
            medicineRepository.updateStock(restockDb.getMedicine().getId(), restockDb.getQuantity());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean restockMedicine(Long id, double previousQuantity) {
        Restock restockDb = restockRepository.findById(id).orElse(null);
        if (restockDb != null) {
            double quantityDifference = restockDb.getQuantity() - previousQuantity;
            medicineRepository.updateStock(restockDb.getMedicine().getId(), quantityDifference);
            return true;
        } else {
            return false;
        }
    }
}

