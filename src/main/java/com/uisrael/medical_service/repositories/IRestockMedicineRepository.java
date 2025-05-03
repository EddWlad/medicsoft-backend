package com.uisrael.medical_service.repositories;

import com.uisrael.medical_service.entities.DispensaryMedicinePK;
import com.uisrael.medical_service.entities.Restock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.UUID;

public interface IRestockMedicineRepository extends IGenericRepository<Restock, DispensaryMedicinePK>{


    @Modifying
    @Query(value = "INSERT INTO restock_medicine(id_restock, id_medicine, quantity, status) VALUES (:idRestock, :idMedicine, :quantity, 1)", nativeQuery = true)
    Integer saveMedicine(@Param("idRestock") UUID idRestock, @Param("idMedicine") UUID idMedicine, @Param("quantity") Integer quantity);

    @Query(value = """
    SELECT 
        m.id_medicine, 
        m.photo,
        m.name,
        rm.quantity,
        m.description,
        m.price
    FROM restock_medicine rm
    JOIN medicine m ON m.id_medicine = rm.id_medicine
    WHERE rm.id_restock = :restockId
""", nativeQuery = true)
    List<Object[]> findRestockDetails(@Param("restockId") UUID restockId);

    @Modifying
    @Query(value = "DELETE FROM restock_medicine WHERE id_restock = :restockId", nativeQuery = true)
    void deleteByRestockId(@Param("restockId") UUID restockId);
}
