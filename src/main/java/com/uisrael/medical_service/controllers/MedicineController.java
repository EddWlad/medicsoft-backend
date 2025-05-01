package com.uisrael.medical_service.controllers;

import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.dtos.MedicineDTO;
import com.uisrael.medical_service.services.IMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/medicine")
public class MedicineController {
    /*@Autowired
    private IMedicineService medicineService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<MedicineDTO> medicineList = medicineService.getAll()
                .stream()
                .map(medicine -> MedicineDTO.builder()
                        .id(medicine.getId())
                        .photo(medicine.getPhoto())
                        .name(medicine.getName())
                        .description(medicine.getDescription())
                        .unitType(medicine.getUnitType())
                        .stock(medicine.getStock())
                        .price(medicine.getPrice())
                        .status(medicine.getStatus())

                        .build())
                .toList();
        return ResponseEntity.ok(medicineList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        Optional<Medicine> medicineOptional = medicineService.findById(id);
        if(medicineOptional.isPresent()){
            Medicine medicine = medicineOptional.get();
            MedicineDTO medicineDTO = MedicineDTO.builder()
                    .id(medicine.getId())
                    .photo(medicine.getPhoto())
                    .name(medicine.getName())
                    .description(medicine.getDescription())
                    .unitType(medicine.getUnitType())
                    .stock(medicine.getStock())
                    .price(medicine.getPrice())
                    .status(medicine.getStatus())

                    .build();
            return ResponseEntity.ok(medicineDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMedicine(@RequestBody MedicineDTO medicineDTO) throws URISyntaxException{
        if(medicineDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }
            medicineService.saveMedicine(Medicine.builder()
                            .id(medicineDTO.getId())
                            .photo(medicineDTO.getPhoto())
                            .name(medicineDTO.getName())
                            .description(medicineDTO.getDescription())
                            .unitType(medicineDTO.getUnitType())
                            .stock(medicineDTO.getStock())
                            .price(medicineDTO.getPrice())
                            .status(medicineDTO.getStatus())

                    .build());
            return ResponseEntity.created(new URI("/api/medicine/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMedicine(@PathVariable Long id, @RequestBody MedicineDTO medicineDTO){
        Optional<Medicine> medicineOptional = medicineService.findById(id);
        if(medicineOptional.isPresent()){
            Medicine medicine = medicineOptional.get();
            medicine.setPhoto(medicineDTO.getPhoto());
            medicine.setName(medicineDTO.getName());
            medicine.setDescription(medicineDTO.getDescription());
            medicine.setUnitType(medicineDTO.getUnitType());
            medicine.setStock(medicineDTO.getStock());
            medicine.setPrice(medicineDTO.getPrice());
            medicine.setStatus(medicineDTO.getStatus());



            medicineService.updateMedicine(id, medicine);
            return ResponseEntity.ok("Medicamento actualizado");
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMedicine(@PathVariable Long id){
        boolean result = medicineService.deleteMedicine(id);
        if(result){
            return ResponseEntity.ok("Medicamento eliminado");
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }*/
}
