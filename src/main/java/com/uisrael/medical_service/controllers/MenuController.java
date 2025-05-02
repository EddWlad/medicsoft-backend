package com.uisrael.medical_service.controllers;

import com.uisrael.medical_service.dtos.MenuDTO;
import com.uisrael.medical_service.services.IMenuService;
import com.uisrael.medical_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final IMenuService menuService;
    private final MapperUtil mapperUtil;

    @PostMapping("/user")
    public ResponseEntity<List<MenuDTO>> getMenusByUser(@RequestBody String username) {
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<MenuDTO> listDTO = mapperUtil.mapList(menuService.getMenusByUsername(username), MenuDTO.class);
        return ResponseEntity.ok(listDTO);
    }
}
