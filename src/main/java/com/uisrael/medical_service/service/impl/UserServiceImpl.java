package com.uisrael.medical_service.service.impl;

import com.uisrael.medical_service.entities.User;
import com.uisrael.medical_service.repositories.IUserRepository;
import com.uisrael.medical_service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<User> getAll() {
        return userRepository.findByStatusNot(0);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User userDb = userRepository.findById(id).orElse(null);
        if(user != null)
        {
            userDb.setName(user.getName());
            userDb.setLastName(user.getLastName());
            userDb.setEmail(user.getEmail());
            userDb.setDateCreate(user.getDateCreate());
            userDb.setPassword(user.getPassword());
            userDb.setIdentification(user.getIdentification());
            userDb.setRole(user.getRole());
            userDb.setUsername(user.getUsername());
            userDb.setStatus(user.getStatus());
            return userRepository.save(userDb);
        }
        else {
            return null;
        }

    }

    @Override
    public boolean deleteUser(Long id) {
        User userDb = userRepository.findById(id).orElse(null);
        if(userDb != null)
        {
            userDb.setStatus(0);
            userRepository.save(userDb);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Long countUser() {
        return userRepository.count();
    }
}
