package com.uisrael.medical_service.repositories;

import com.uisrael.medical_service.entities.Dispensary;
import com.uisrael.medical_service.entities.DispensaryMedicinePK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IDispensaryMedicineRepository extends IGenericRepository<Dispensary, DispensaryMedicinePK>{
    // 1. Guardar relación dispensación-medicina con cantidad
    @Modifying
    @Query(value = """
        INSERT INTO dispensary_medicine(id_dispensary, id_medicine, quantity, status)
        VALUES (:idDispensary, :idMedicine, :quantity, 1)
    """, nativeQuery = true)
    Integer saveMedicine(@Param("idDispensary") UUID idDispensary,
                         @Param("idMedicine") UUID idMedicine,
                         @Param("quantity") Integer quantity);

    // 2. Obtener detalle de una dispensación con sus medicamentos
    @Query(value = """
        SELECT 
            m.id_medicine, 
            m.photo,
            m.name,
            dm.quantity,
            m.description,
            m.price
        FROM dispensary_medicine dm
        JOIN medicine m ON m.id_medicine = dm.id_medicine
        WHERE dm.id_dispensary = :dispensaryId
    """, nativeQuery = true)
    List<Object[]> findDispensaryDetails(@Param("dispensaryId") UUID dispensaryId);

    // 3. Eliminar relaciones de una dispensación específica
    @Modifying
    @Query(value = "DELETE FROM dispensary_medicine WHERE id_dispensary = :dispensaryId", nativeQuery = true)
    void deleteByDispensaryId(@Param("dispensaryId") UUID dispensaryId);
}
