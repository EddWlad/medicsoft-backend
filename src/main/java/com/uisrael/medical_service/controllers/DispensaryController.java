package com.uisrael.medical_service.controllers;


import com.uisrael.medical_service.dtos.DispensaryListMedicineDTO;
import com.uisrael.medical_service.dtos.DispensaryDetailMedicineDTO;
import com.uisrael.medical_service.dtos.DispensaryDTO;
import com.uisrael.medical_service.entities.Dispensary;
import com.uisrael.medical_service.services.IDispensaryService;
import com.uisrael.medical_service.utils.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dispensaries")
@RequiredArgsConstructor
public class DispensaryController {
    private final IDispensaryService dispensaryService;
    private final MapperUtil mapperUtil;

    // POST - crear dispensación
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody DispensaryListMedicineDTO dto) throws Exception {
        Dispensary obj1 = mapperUtil.map(dto.getDispensary(), Dispensary.class);
        Dispensary obj = dispensaryService.saveTransactional(obj1, dto.getListMedicine());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getIdDispensary())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // GET - listar todas las dispensaciones
    @GetMapping
    public ResponseEntity<List<DispensaryDetailMedicineDTO>> findAll() throws Exception {
        List<DispensaryDetailMedicineDTO> list = dispensaryService.findAllWithMedicines();
        return ResponseEntity.ok(list);
    }

    // GET - obtener una dispensación por ID
    @GetMapping("/{id}")
    public ResponseEntity<DispensaryDetailMedicineDTO> findById(@PathVariable("id") UUID id) throws Exception {
        DispensaryDetailMedicineDTO dto = dispensaryService.findWithMedicines(id);
        return ResponseEntity.ok(dto);
    }

    // PUT - actualizar una dispensación
    @PutMapping("/{id}")
    public ResponseEntity<DispensaryDTO> update(@PathVariable("id") UUID id,
                                                @Valid @RequestBody DispensaryListMedicineDTO dto) throws Exception {
        Dispensary updated = dispensaryService.updateTransactional(
                id,
                mapperUtil.map(dto.getDispensary(), Dispensary.class),
                dto.getListMedicine()
        );
        return ResponseEntity.ok(mapperUtil.map(updated, DispensaryDTO.class));
    }

    // DELETE - eliminar lógicamente una dispensación
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) throws Exception {
        boolean deleted = dispensaryService.softDeleteWithRollback(id);
        if (deleted) {
            return ResponseEntity.ok("Dispensación eliminada exitosamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
