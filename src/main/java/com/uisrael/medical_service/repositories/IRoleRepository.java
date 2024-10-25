package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
    List<Role> findByStatusNot(Integer status);
}
