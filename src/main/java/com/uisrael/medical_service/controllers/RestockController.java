package com.uisrael.medical_service.controllers;


import com.uisrael.medical_service.dtos.*;


import com.uisrael.medical_service.entities.Restock;

import com.uisrael.medical_service.services.IRestockService;
import com.uisrael.medical_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.util.List;

import java.util.UUID;


@RestController
@RequestMapping("restocks")
@RequiredArgsConstructor
public class RestockController {

    private final IRestockService restockService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<RestockDetailMedicineDTO>> findAll() throws Exception{
        List<RestockDetailMedicineDTO> list = restockService.findAllWithMedicines();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestockDetailMedicineDTO> findById(@PathVariable("id") UUID id) throws Exception{
        RestockDetailMedicineDTO dto = restockService.findWithMedicines(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody RestockListMedicineDTO dto) throws Exception{
        Restock obj1 = mapperUtil.map(dto.getRestock(), Restock.class);
        List<RestockDetailDTO> list = dto.getListMedicine();

        Restock obj = restockService.saveTransactional(obj1, list);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getIdRestock())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestockDTO> update(@PathVariable("id") UUID id,
                                             @RequestBody RestockListMedicineDTO dto) throws Exception {
        Restock updated = restockService.updateWithMedicines(
                id,
                mapperUtil.map(dto.getRestock(), Restock.class),
                dto.getListMedicine()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(updated.getIdRestock())
                .toUri();

        return ResponseEntity.ok(mapperUtil.map(updated, RestockDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) throws Exception{
        boolean deleted = restockService.softDeleteWithRollback(id);

        if (deleted) {
            return ResponseEntity.ok("Restock eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restock no encontrado o no pudo eliminarse.");
        }
    }
}
