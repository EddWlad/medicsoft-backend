package com.uisrael.medical_service.services;


import com.uisrael.medical_service.dtos.RestockDetailDTO;
import com.uisrael.medical_service.dtos.RestockDetailMedicineDTO;
import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.entities.Restock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRestockService extends IGenericService<Restock, UUID> {

    Long countRestock();
    Restock saveTransactional(Restock restock, List<RestockDetailDTO> medicines) throws Exception;
    RestockDetailMedicineDTO findWithMedicines(UUID id) throws Exception;
    List<RestockDetailMedicineDTO> findAllWithMedicines() throws Exception;

    Restock updateWithMedicines(UUID id, Restock restock, List<RestockDetailDTO> medicines) throws Exception;

    boolean softDeleteWithRollback(UUID id) throws Exception;

}
