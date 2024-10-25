package com.uisrael.medical_service.repositories;


import com.uisrael.medical_service.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicineRepository extends JpaRepository<Medicine,Long> {
    List<Medicine> findByStatusNot(Integer status);
    @Modifying
    @Query("UPDATE Medicine m SET m.stock = m.stock + :quantity WHERE m.id = :medicineId")
    void updateStock(@Param("medicineId") Long medicineId, @Param("quantity") Double quantity);

    @Modifying
    @Query("UPDATE Medicine m SET m.stock = m.stock - :quantity WHERE m.id = :medicineId AND m.stock >= :quantity")
    int reduceStock(@Param("medicineId") Long medicineId, @Param("quantity") Double quantity);

    @Modifying
    @Query("UPDATE Medicine m SET m.stock = m.stock + :quantity WHERE m.id = :medicineId")
    int increaseStock(@Param("medicineId") Long medicineId, @Param("quantity") Double quantity);
}
