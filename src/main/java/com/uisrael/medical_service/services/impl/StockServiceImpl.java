package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.Stock;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.repositories.IStockRepository;
import com.uisrael.medical_service.services.IStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockServiceImpl extends GenericServiceImpl<Stock, UUID> implements IStockService {
    private final IStockRepository stockRepository;

    @Override
    protected IGenericRepository<Stock, UUID> getRepo() {
        return stockRepository;
    }

    @Override
    public Long countStock() {
        return stockRepository.count();
    }
}
