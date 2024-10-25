package com.uisrael.medical_service.controller;


import com.uisrael.medical_service.entities.Role;
import com.uisrael.medical_service.entities.User;
import com.uisrael.medical_service.model.RoleDTO;
import com.uisrael.medical_service.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<RoleDTO> roleList = roleService.getAll()
                .stream()
                .map(role -> RoleDTO.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .description(role.getDescription())
                        .status(role.getStatus())

                        .build())
                .toList();
        return ResponseEntity.ok(roleList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Role> roleOptional = roleService.findById(id);
        if(roleOptional.isPresent())
        {
            Role role = roleOptional.get();
            RoleDTO roleDTO = RoleDTO.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .description(role.getDescription())

                    .status((role.getStatus()))
                    .build();
            return ResponseEntity.ok(roleDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRole(@RequestBody RoleDTO roleDTO) throws URISyntaxException{
        if(roleDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        roleService.saveRole(Role.builder()
                .name(roleDTO.getName())
                .description(roleDTO.getDescription())

                .status(roleDTO.getStatus())
                .build());
        return ResponseEntity.created(new URI("/api/role/save")).build();
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO ){
        Optional<Role> roleOptional = roleService.findById(id);
        if(roleOptional.isPresent()) {
            Role role = roleOptional.get();
            role.setName(roleDTO.getName());
            role.setDescription(roleDTO.getDescription());
            role.setStatus(roleDTO.getStatus());


            roleService.updateRole(id, role);
            return ResponseEntity.ok("Rol actualizado");
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id){
        boolean result = roleService.deleteRole(id);
        if(result){
            return ResponseEntity.ok("Rol eliminado");
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
