package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IMedicineRepository extends IGenericRepository<Medicine,UUID> {
    @Query("SELECT u FROM Medicine u WHERE u.name = :name AND u.status IN (1, 2)")
    Optional<Medicine> findByNameStatus(@Param("name") String name);

    @Query("SELECT u FROM Medicine u WHERE u.name = :name AND u.status = 0")
    Optional<Medicine> findDeletedMedicine(@Param("name") String name);

    @Query("SELECT m FROM Medicine m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Medicine> findByNameIgnoreCase(@Param("name") String name);
}
