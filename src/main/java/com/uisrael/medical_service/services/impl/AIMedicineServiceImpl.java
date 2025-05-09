package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.dtos.AIResponseStockDTO;

import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.repositories.IMedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AIMedicineServiceImpl implements Function<AIMedicineServiceImpl.Request, AIMedicineServiceImpl.Response> {

    private final IMedicineRepository medicineRepository;
    public record Request(String medicine){}
    public record Response(List<AIResponseStockDTO> medicines){}

    @Override
    public Response apply(Request request) {
        List<Medicine> medicines = medicineRepository.findByNameIgnoreCase(request.medicine);

        List<AIResponseStockDTO> dtoList = medicines.stream()
                .map(med -> new AIResponseStockDTO(
                        med.getName(),
                        med.getDescription(),
                        med.getUnitType(),
                        med.getPrice(),
                        med.getStock() != null ? med.getStock().getQuantity() : 0
                )).toList();

        return new Response(dtoList);
    }
}
