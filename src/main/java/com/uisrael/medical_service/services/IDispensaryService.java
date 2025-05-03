package com.uisrael.medical_service.services;

import com.itextpdf.text.DocumentException;
import com.uisrael.medical_service.dtos.DispensaryDetailDTO;
import com.uisrael.medical_service.dtos.DispensaryDetailMedicineDTO;
import com.uisrael.medical_service.entities.Dispensary;


import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDispensaryService extends IGenericService<Dispensary, UUID> {

    Dispensary saveTransactional(Dispensary dispensary, List<DispensaryDetailDTO> medicines) throws Exception;

    DispensaryDetailMedicineDTO findWithMedicines(UUID id) throws Exception;

    List<DispensaryDetailMedicineDTO> findAllWithMedicines() throws Exception;

    Dispensary updateTransactional(UUID id, Dispensary dispensary, List<DispensaryDetailDTO> medicines) throws Exception;

    boolean softDeleteWithRollback(UUID id) throws Exception;
}
