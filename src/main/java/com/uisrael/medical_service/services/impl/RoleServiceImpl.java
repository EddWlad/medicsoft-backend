package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.Role;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.repositories.IRoleRepository;
import com.uisrael.medical_service.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends GenericServiceImpl<Role, UUID> implements IRoleService {

    private final IRoleRepository roleRepository;



    @Override
    public Long countRole() {
        return roleRepository.count();
    }

    @Override
    protected IGenericRepository<Role, UUID> getRepo() {
        return roleRepository;
    }
}
