package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.User;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.repositories.IUserRepository;
import com.uisrael.medical_service.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends GenericServiceImpl<User, UUID> implements IUserService {

    private final IUserRepository userRepository;

    @Override
    protected IGenericRepository<User, UUID> getRepo() {
        return userRepository;
    }


    @Override
    public Long countUser() {
        return userRepository.count();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
