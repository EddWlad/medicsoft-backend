package com.uisrael.medical_service.services;

import com.uisrael.medical_service.entities.Menu;

import java.util.List;

public interface IMenuService {
    List<Menu> getMenusByUsername(String username);
}
