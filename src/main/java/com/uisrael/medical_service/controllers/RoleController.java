package com.uisrael.medical_service.controllers;

import com.uisrael.medical_service.dtos.RoleDTO;
import com.uisrael.medical_service.entities.Role;
import com.uisrael.medical_service.services.IRoleService;
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
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() throws Exception {
        List<RoleDTO> list = mapperUtil.mapList(roleService.findAll(),
                RoleDTO.class);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable("id") UUID id) throws Exception {
        RoleDTO obj = mapperUtil.map(roleService.findById(id), RoleDTO.class);
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody RoleDTO roleDTO) throws Exception{
        Role obj = roleService.save(mapperUtil.map(roleDTO, Role.class));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getIdRole()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(@PathVariable("id") UUID id, @RequestBody RoleDTO roleDTO) throws Exception{
        Role obj = roleService.update(mapperUtil.map(roleDTO, Role.class), id);

        return ResponseEntity.ok(mapperUtil.map(obj, RoleDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) throws Exception{
        roleService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<RoleDTO> findByIdHateoas(@PathVariable("id") UUID id) throws Exception {
        Role obj = roleService.findById(id);
        EntityModel<RoleDTO> resource = EntityModel.of(mapperUtil.map(obj, RoleDTO.class));

        //Generar link informativo
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("role-self-info"));
        resource.add(link2.withRel("role-all-info"));
        return resource;
    }

}
