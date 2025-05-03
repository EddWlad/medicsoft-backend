package com.uisrael.medical_service.controllers;

import com.uisrael.medical_service.dtos.MedicineDTO;
import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.dtos.MedicineDTO;
import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.services.IMedicineService;
import com.uisrael.medical_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/medicines")
@RequiredArgsConstructor
public class MedicineController {
    private final IMedicineService medicineService;
    private final MapperUtil mapperUtil;

    // 📌 1️⃣ Obtener todas las medicinas
    @GetMapping
    public ResponseEntity<List<MedicineDTO>> findAll() throws Exception  {
        List<MedicineDTO> list = mapperUtil.mapList(medicineService.findAll(), MedicineDTO.class);
        return ResponseEntity.ok(list);
    }

    // 📌 2️⃣ Obtener un medicamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicineDTO> findById(@PathVariable("id") UUID id) throws Exception {
        MedicineDTO obj = mapperUtil.map(medicineService.findById(id), MedicineDTO.class);
        return ResponseEntity.ok(obj);
    }

    // 📌 3️⃣ Crear un nuevo medicina
    @PostMapping
    public ResponseEntity<MedicineDTO> save(@RequestBody MedicineDTO medicineDTO) throws Exception {
        Medicine obj = medicineService.create(medicineDTO);

        URI location = fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getIdMedicine())
                .toUri();

        return ResponseEntity.created(location).body(mapperUtil.map(obj, MedicineDTO.class));
    }

    // 📌 4️⃣ Actualizar una medicina
    @PutMapping("/{id}")
    public ResponseEntity<MedicineDTO> update(@PathVariable("id") UUID id, @RequestBody MedicineDTO MedicineDTO) throws Exception  {
        Medicine obj = medicineService.update(mapperUtil.map(MedicineDTO, Medicine.class), id);
        return ResponseEntity.ok(mapperUtil.map(obj, MedicineDTO.class));
    }

    // 📌 5️⃣ Eliminar una medicina (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) throws Exception {
        boolean deleted = medicineService.softDelete(id);

        if (deleted) {
            return ResponseEntity.ok("Medicine and its stock deleted (soft) successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medicine not found with ID: " + id);
        }
    }

    // 📌 6️⃣ Obtener medicina con HATEOAS
    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicineDTO> findByIdHateoas(@PathVariable("id") UUID id) throws Exception {
        Medicine obj = medicineService.findById(id);
        EntityModel<MedicineDTO> resource = EntityModel.of(mapperUtil.map(obj, MedicineDTO.class));

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("Medicine-self-info"));
        resource.add(link2.withRel("Medicine-all-info"));
        return resource;
    }

}
