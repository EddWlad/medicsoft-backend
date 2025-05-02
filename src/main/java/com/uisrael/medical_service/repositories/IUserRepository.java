package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends IGenericRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.identification = :identification AND u.status IN (1, 2)")
    Optional<User> findByIdentificationStatus(@Param("identification") String identification);

    @Query("SELECT u FROM User u WHERE u.identification = :identification AND u.status = 0")
    Optional<User> findDeletedUser(@Param("identification") String identification);
}
