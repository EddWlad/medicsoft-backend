package com.uisrael.medical_service.controllers;

import com.uisrael.medical_service.dtos.MedicalHistoryDTO;
import com.uisrael.medical_service.entities.MedicalHistory;

import com.uisrael.medical_service.services.IMedicalHistoryService;
import com.uisrael.medical_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("medical-histories")
@RequiredArgsConstructor
public class MedicalHistoryController {

    private final IMedicalHistoryService medicalHistoryService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<MedicalHistoryDTO>> findAll() throws Exception{
        List<MedicalHistoryDTO> list = mapperUtil.mapList(medicalHistoryService.findAll(), MedicalHistoryDTO.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalHistoryDTO> findById(@PathVariable("id") UUID id) throws Exception{
        MedicalHistoryDTO medicalHistoryDTO = mapperUtil.map(medicalHistoryService.findById(id), MedicalHistoryDTO.class);
        return ResponseEntity.ok(medicalHistoryDTO);
    }

    @PostMapping
    public ResponseEntity<MedicalHistoryDTO> save(@RequestBody MedicalHistoryDTO medicalHistoryDTO) throws Exception{
        MedicalHistory obj = medicalHistoryService.save(mapperUtil.map(medicalHistoryDTO, MedicalHistory.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getIdMedicalHistory())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalHistoryDTO> update(@PathVariable("id") UUID id, @RequestBody MedicalHistoryDTO medicalHistoryDTO) throws Exception{
        MedicalHistory obj = medicalHistoryService.update(mapperUtil.map(medicalHistoryDTO, MedicalHistory.class), id);
        return ResponseEntity.ok(mapperUtil.map(obj, MedicalHistoryDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) throws Exception{
        medicalHistoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
