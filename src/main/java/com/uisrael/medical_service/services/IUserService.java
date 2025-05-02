package com.uisrael.medical_service.services;



import com.uisrael.medical_service.dtos.UserDTO;
import com.uisrael.medical_service.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService extends IGenericService<User, UUID> {

    Long countUser();

    User create(UserDTO dto) throws Exception;
}
