package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    List<User> findByStatusNot(Integer status);
    Optional<User> findByEmail(String email);
}
