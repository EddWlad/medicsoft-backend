package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.dtos.UserDTO;
import com.uisrael.medical_service.entities.User;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.repositories.IUserRepository;
import com.uisrael.medical_service.services.IUserService;
import com.uisrael.medical_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends GenericServiceImpl<User, UUID> implements IUserService {

    private final IUserRepository userRepository;
    private final MapperUtil mapperUtil;

    @Override
    protected IGenericRepository<User, UUID> getRepo() {
        return userRepository;
    }


    @Override
    public Long countUser() {
        return userRepository.count();
    }

    @Transactional
    public User create(UserDTO dto) throws Exception {
        String idn = dto.getIdentification();

        Optional<User> activeUserOpt = userRepository.findByIdentificationStatus(idn);
        if (activeUserOpt.isPresent()) {
            throw new Exception("User already exist.");
        }

        Optional<User> deletedUserOpt = userRepository.findDeletedUser(idn);
        if (deletedUserOpt.isPresent()) {
            User existingUser = deletedUserOpt.get();
            existingUser.setStatus(1);
            existingUser.setMedicalHistory(dto.getMedicalHistory());
            existingUser.getMedicalHistory().setStatus(1);
            return userRepository.save(existingUser);
        }

        // Crear nuevo usuario
        User user = mapperUtil.map(dto, User.class);
        user.setStatus(1);
        user.getMedicalHistory().setStatus(1);
        return userRepository.save(user);
    }

    @Override
    public boolean softDelete(UUID id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();
        user.setStatus(0);

        if (user.getMedicalHistory() != null) {
            user.getMedicalHistory().setStatus(0);
        }

        userRepository.save(user); // CascadeType.ALL asegura que se actualice el historial también
        return true;
    }

}
