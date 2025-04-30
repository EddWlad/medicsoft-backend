package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Role;

import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface IRoleRepository extends IGenericRepository<Role, UUID> {

}
