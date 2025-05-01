package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.User;

import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends IGenericRepository<User, UUID> {

    /*Optional<User> findByEmail(String email);*/
}
