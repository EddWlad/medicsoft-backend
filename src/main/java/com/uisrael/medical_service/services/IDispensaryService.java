package com.uisrael.medical_service.services;

import com.itextpdf.text.DocumentException;
import com.uisrael.medical_service.entities.Dispensary;


import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDispensaryService extends IGenericService<Dispensary, UUID> {

    Long countDispensary();

    public boolean dispensaryMedicine(UUID id);

    public boolean dispensaryMedicine(UUID id,double previousQuantity);

    ByteArrayInputStream generatePdfReport() throws DocumentException;

    ByteArrayInputStream generatePdfReportForDispensary(UUID id) throws Exception;
}
