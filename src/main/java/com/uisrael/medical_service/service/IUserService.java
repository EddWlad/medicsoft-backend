package com.uisrael.medical_service.service;



import com.uisrael.medical_service.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAll();
    Optional<User> findById(Long id);
    User saveUser(User user);
    User updateUser(Long id, User user);
    public boolean deleteUser(Long id);
    Long countUser();

    Optional<User> findByEmail(String email);
}
