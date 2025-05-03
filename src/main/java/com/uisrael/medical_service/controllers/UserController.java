package com.uisrael.medical_service.controllers;

import com.uisrael.medical_service.dtos.UserDTO;
import com.uisrael.medical_service.entities.User;
import com.uisrael.medical_service.services.IUserService;
import com.uisrael.medical_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final IUserService userService;
    private final MapperUtil mapperUtil;

    // 📌 1️⃣ Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() throws Exception  {
        List<UserDTO> list = mapperUtil.mapList(userService.findAll(), UserDTO.class);
        return ResponseEntity.ok(list);
    }

    // 📌 2️⃣ Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") UUID id) throws Exception {
        UserDTO obj = mapperUtil.map(userService.findById(id), UserDTO.class);
        return ResponseEntity.ok(obj);
    }

    // 📌 3️⃣ Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) throws Exception {
        User obj = userService.create(userDTO);

        URI location = fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getIdUser())
                .toUri();

        return ResponseEntity.created(location).body(mapperUtil.map(obj, UserDTO.class));
    }

    // 📌 4️⃣ Actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id") UUID id, @RequestBody UserDTO userDTO) throws Exception  {
        User obj = userService.update(mapperUtil.map(userDTO, User.class), id);
        return ResponseEntity.ok(mapperUtil.map(obj, UserDTO.class));
    }

    // 📌 5️⃣ Eliminar un usuario (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) throws Exception {
        boolean deleted = userService.softDelete(id);

        if (deleted) {
            return ResponseEntity.ok("User and medical history deleted (soft) successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + id);
        }
    }

    // 📌 6️⃣ Obtener usuario con HATEOAS
    @GetMapping("/hateoas/{id}")
    public EntityModel<UserDTO> findByIdHateoas(@PathVariable("id") UUID id) throws Exception {
        User obj = userService.findById(id);
        EntityModel<UserDTO> resource = EntityModel.of(mapperUtil.map(obj, UserDTO.class));

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("user-self-info"));
        resource.add(link2.withRel("user-all-info"));
        return resource;
    }
}