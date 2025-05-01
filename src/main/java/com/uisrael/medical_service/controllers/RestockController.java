package com.uisrael.medical_service.controllers;


import com.uisrael.medical_service.entities.Restock;
import com.uisrael.medical_service.dtos.RestockDTO;
import com.uisrael.medical_service.services.IRestockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/restock")
public class RestockController {
    /*@Autowired
    private IRestockService restockService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<RestockDTO> restockList = restockService.getAll()
                .stream()
                .map(restock -> RestockDTO.builder()
                        .id(restock.getId())
                        .restockDate(restock.getRestockDate())
                        .medicine(restock.getMedicine())
                        .quantity(restock.getQuantity())
                        .observation(restock.getObservation())
                        .status(restock.getStatus())
                        .build())
                .toList();
        return ResponseEntity.ok(restockList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?>findById(@PathVariable Long id){
        Optional<Restock> restockOptional = restockService.findById(id);
        if(restockOptional.isPresent()){
            Restock restock= restockOptional.get();
            RestockDTO restockDTO = RestockDTO.builder()
                    .id(restock.getId())
                    .restockDate(restock.getRestockDate())
                    .medicine(restock.getMedicine())
                    .quantity(restock.getQuantity())
                    .observation(restock.getObservation())
                    .status(restock.getStatus())
                    .build();
            return ResponseEntity.ok(restockDTO);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/save")
    public ResponseEntity<?> saveRestock(@RequestBody RestockDTO restockDTO) throws URISyntaxException {
        if (restockDTO.getQuantity() <= 0) {
            return ResponseEntity.badRequest().body("La cantidad debe ser mayor a cero.");
        }
        Restock restock = Restock.builder()
                .id(restockDTO.getId())
                .restockDate(restockDTO.getRestockDate())
                .medicine(restockDTO.getMedicine())
                .quantity(restockDTO.getQuantity())
                .observation(restockDTO.getObservation())
                .status(restockDTO.getStatus())
                .build();
        Restock savedRestock = restockService.saveRestock(restock);

        if (savedRestock != null && restockService.restockMedicine(savedRestock.getId())) {
            return ResponseEntity.created(new URI("/api/restock/save")).body("Reabastecimiento guardado y stock actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el stock del medicamento.");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRestock(@PathVariable Long id, @RequestBody RestockDTO restockDTO){
        Optional<Restock> restockOptional = restockService.findById(id);
        if (restockOptional.isPresent()) {
            Restock restock = restockOptional.get();
            double previousQuantity = restock.getQuantity();

            restock.setRestockDate(restockDTO.getRestockDate());
            restock.setQuantity(restockDTO.getQuantity());
            restock.setObservation(restockDTO.getObservation());
            restock.setStatus(restockDTO.getStatus());

            Restock updatedRestock = restockService.updateRestock(id, restock);

            if (updatedRestock != null) {

                boolean stockUpdated = restockService.restockMedicine(updatedRestock.getId(), previousQuantity);

                if (stockUpdated) {
                    return ResponseEntity.ok("Reabastecimiento actualizado y stock de medicina actualizado correctamente.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el stock de la medicina.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el reabastecimiento.");
            }
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRestock(@PathVariable Long id){
        boolean result = restockService.deleteRestock(id);
        if(result){
            return ResponseEntity.ok("Abastecimiento eliminado");
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }*/
}
