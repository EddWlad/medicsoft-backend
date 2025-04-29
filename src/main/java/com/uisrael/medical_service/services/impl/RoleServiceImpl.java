package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.Role;
import com.uisrael.medical_service.repositories.IRoleRepository;
import com.uisrael.medical_service.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role role) {
        Role roleDb = roleRepository.findById(id).orElse(null);
        if(role != null)
        {
            roleDb.setName(role.getName());
            roleDb.setDescription((role.getDescription()));
            roleDb.setStatus(role.getStatus());
            return roleRepository.save(roleDb);
        }
        else {
            return null;
        }

    }

    @Override
    public boolean deleteRole(Long id) {
        Role roleDb = roleRepository.findById(id).orElse(null);
        if(roleDb != null)
        {
            roleDb.setStatus(0);
            roleRepository.save(roleDb);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Long countRole() {
        return roleRepository.count();
    }
}
