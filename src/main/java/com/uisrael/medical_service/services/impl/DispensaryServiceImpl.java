package com.uisrael.medical_service.services.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.uisrael.medical_service.entities.Dispensary;
import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.repositories.IDispensaryRepository;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.repositories.IMedicineRepository;
import com.uisrael.medical_service.services.IDispensaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DispensaryServiceImpl extends GenericServiceImpl<Dispensary, UUID> implements IDispensaryService {

    private final IDispensaryRepository dispensaryRepository;
    private final IMedicineRepository medicineRepository;


    @Override
    protected IGenericRepository<Dispensary, UUID> getRepo() {
        return dispensaryRepository;
    }

    @Override
    public Long countDispensary() {
        return dispensaryRepository.count();
    }

    /*@Override
    @Transactional
    public boolean dispensaryMedicine(UUID id) {
        Dispensary dispensaryDb = dispensaryRepository.findById(id).orElse(null);
        if (dispensaryDb != null) {
                medicineRepository.reduceStock(dispensaryDb.getMedicine().getIdMedicine(), dispensaryDb.getQuantity());
                return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean dispensaryMedicine(UUID id, double previousQuantity) {
        Dispensary dispensaryDb = dispensaryRepository.findById(id).orElse(null);
        if (dispensaryDb != null) {
            double quantityDifference = dispensaryDb.getQuantity() - previousQuantity;

            if (quantityDifference > 0) {
                return medicineRepository.reduceStock(dispensaryDb.getMedicine().getIdMedicine(), quantityDifference) > 0;
            }

            else if (quantityDifference < 0) {
                medicineRepository.increaseStock(dispensaryDb.getMedicine().getIdMedicine(), Math.abs(quantityDifference));
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public ByteArrayInputStream generatePdfReport() throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        List<Dispensary> dispensaries = dispensaryRepository.findAll();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            for (Dispensary dispensary : dispensaries) {
                document.add(new Paragraph("Dispensary ID: " + dispensary.getIdDispensary()));
                document.add(new Paragraph("Medicine Name: " + dispensary.getMedicine().getName()));
                document.add(new Paragraph("Quantity: " + dispensary.getQuantity()));
                document.add(new Paragraph("Patient: " + dispensary.getPatient().getName()));
                document.add(new Paragraph("Dispensay Date: " + dispensary.getDispensayDate()));
                document.add(new Paragraph("---------------------------------------------"));
            }

            document.close();
        } catch (DocumentException e) {
            throw new DocumentException("Error creating PDF");
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream generatePdfReportForDispensary(UUID id) throws Exception {
        Optional<Dispensary> dispensaryOptional = dispensaryRepository.findById(id);
        if (!dispensaryOptional.isPresent()) {
            throw new Exception("No se encontró la dispensación con ID: " + id);
        }
        Dispensary dispensary = dispensaryOptional.get(); // Fetch the Dispensary object using the ID
        return generatePdfForSingleDispensary(dispensary); // Pass the fetched object to the PDF generation method
    }

    public ByteArrayInputStream generatePdfForSingleDispensary(Dispensary dispensary) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Ticket de Dispensación", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);


            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("ID de Dispensación: " + dispensary.getIdDispensary(), normalFont));
            document.add(new Paragraph("Paciente: " + dispensary.getPatient().getName() + " " + dispensary.getPatient().getLastName(), normalFont));
            document.add(new Paragraph("Medicina: " + dispensary.getMedicine().getName(), normalFont));
            document.add(new Paragraph("Cantidad: " + dispensary.getQuantity(), normalFont));
            document.add(new Paragraph("Usuario: " + dispensary.getUser().getUsername(), normalFont));
            document.add(new Paragraph("Fecha: " + dispensary.getDispensayDate(), normalFont));

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }*/


}
