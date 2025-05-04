package com.uisrael.medical_service.controllers;

import com.uisrael.medical_service.dtos.DiagnosticDTO;
import com.uisrael.medical_service.entities.Diagnostic;
import com.uisrael.medical_service.services.IDiagnosticService;
import com.uisrael.medical_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/diagnostics")
@RequiredArgsConstructor
public class DiagnosticController {
    private final IDiagnosticService diagnosticService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<DiagnosticDTO>> findAll() throws Exception {
        List<DiagnosticDTO> list = mapperUtil.mapList(diagnosticService.findAll(),
                DiagnosticDTO.class);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticDTO> findById(@PathVariable("id") UUID id) throws Exception {
        DiagnosticDTO obj = mapperUtil.map(diagnosticService.findById(id), DiagnosticDTO.class);
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody DiagnosticDTO diagnosticDTO) throws Exception{
        Diagnostic obj = diagnosticService.save(mapperUtil.map(diagnosticDTO, Diagnostic.class));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getIdDiagnostic()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticDTO> update(@PathVariable("id") UUID id, @RequestBody DiagnosticDTO diagnosticDTO) throws Exception{
        Diagnostic obj = diagnosticService.update(mapperUtil.map(diagnosticDTO, Diagnostic.class), id);

        return ResponseEntity.ok(mapperUtil.map(obj, DiagnosticDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) throws Exception{
        diagnosticService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<DiagnosticDTO> findByIdHateoas(@PathVariable("id") UUID id) throws Exception {
        Diagnostic obj = diagnosticService.findById(id);
        EntityModel<DiagnosticDTO> resource = EntityModel.of(mapperUtil.map(obj, DiagnosticDTO.class));

        //Generar link informativo
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("Diagnostic-self-info"));
        resource.add(link2.withRel("Diagnostic-all-info"));
        return resource;
    }

}
