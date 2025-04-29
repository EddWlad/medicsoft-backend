package com.uisrael.medical_service.services;

import com.itextpdf.text.DocumentException;
import com.uisrael.medical_service.entities.Dispensary;


import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

public interface IDispensaryService {
    List<Dispensary> getAll();
    Optional<Dispensary> findById(Long id);
    Dispensary saveDispensary(Dispensary dispensary);
    Dispensary updateDispensary(Long id, Dispensary dispensary);
    public boolean deleteDispensary(Long id);
    Long countDispensary();

    public boolean dispensaryMedicine(Long id);

    public boolean dispensaryMedicine(Long id,double previousQuantity);

    ByteArrayInputStream generatePdfReport() throws DocumentException;

    ByteArrayInputStream generatePdfReportForDispensary(Long id) throws Exception;
}
