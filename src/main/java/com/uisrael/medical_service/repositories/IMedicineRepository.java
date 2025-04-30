package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IMedicineRepository extends IGenericRepository<Medicine,UUID> {
    List<Medicine> findByStatusNot(Integer status);
    @Modifying
    @Query("UPDATE Medicine m SET m.stock = m.stock + :quantity WHERE m.id = :medicineId")
    void updateStock(@Param("medicineId") UUID medicineId, @Param("quantity") Double quantity);

    @Modifying
    @Query("UPDATE Medicine m SET m.stock = m.stock - :quantity WHERE m.id = :medicineId AND m.stock >= :quantity")
    int reduceStock(@Param("medicineId") UUID medicineId, @Param("quantity") Double quantity);

    @Modifying
    @Query("UPDATE Medicine m SET m.stock = m.stock + :quantity WHERE m.id = :medicineId")
    int increaseStock(@Param("medicineId") UUID medicineId, @Param("quantity") Double quantity);
}
