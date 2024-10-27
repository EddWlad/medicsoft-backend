package com.uisrael.medical_service.controller;


import com.uisrael.medical_service.entities.User;
import com.uisrael.medical_service.model.LoginRequestDTO;
import com.uisrael.medical_service.model.UserDTO;
import com.uisrael.medical_service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://10.0.2.2:5000")
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<UserDTO> userList = userService.getAll()
                .stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .dateCreate(user.getDateCreate())
                        .identification(user.getIdentification())
                        .name(user.getName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .role(user.getRole())
                        .status(user.getStatus())

                        .build())
                .toList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isPresent())
        {
            User user = userOptional.get();
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .dateCreate(user.getDateCreate())
                    .identification(user.getIdentification())
                    .name(user.getName())
                    .lastName(user.getLastName())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .status(user.getStatus())

                    .build();
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        if(userDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        userService.saveUser(User.builder()
                .id(userDTO.getId())
                .dateCreate(userDTO.getDateCreate())
                .identification(userDTO.getIdentification())
                .name(userDTO.getName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .status(userDTO.getStatus())
                .build());
        return ResponseEntity.created(new URI("/api/user/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO ){
        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setDateCreate(userDTO.getDateCreate());
            user.setIdentification((userDTO.getIdentification()));
            user.setName(userDTO.getName());
            user.setLastName(userDTO.getLastName());
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setRole(userDTO.getRole());
            user.setStatus(userDTO.getStatus());


            userService.updateUser(id, user);
            return ResponseEntity.ok("Usuario actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Optional<User> userOptional = userService.findByEmail(loginRequestDTO.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(loginRequestDTO.getPassword())) {
                UserDTO userDTO = UserDTO.builder()
                        .id(user.getId())
                        .dateCreate(user.getDateCreate())
                        .identification(user.getIdentification())
                        .name(user.getName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .status(user.getStatus())
                        .build();

                return ResponseEntity.ok(userDTO);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        boolean result = userService.deleteUser(id);
        if(result){
            return ResponseEntity.ok("Usuario eliminado");
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
