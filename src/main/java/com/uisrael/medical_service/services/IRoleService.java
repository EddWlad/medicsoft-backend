package com.uisrael.medical_service.services;
import com.uisrael.medical_service.entities.Role;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRoleService extends IGenericService<Role, UUID>{

    Long countRole();
}
