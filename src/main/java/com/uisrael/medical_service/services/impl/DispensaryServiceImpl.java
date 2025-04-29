package com.uisrael.medical_service.services.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.uisrael.medical_service.entities.Dispensary;
import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.repositories.IDispensaryRepository;
import com.uisrael.medical_service.repositories.IMedicineRepository;
import com.uisrael.medical_service.services.IDispensaryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class DispensaryServiceImpl implements IDispensaryService {

    @Autowired
    private IDispensaryRepository dispensaryRepository;

    @Autowired
    private IMedicineRepository medicineRepository;
    @Override
    public List<Dispensary> getAll() {
        return dispensaryRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Dispensary> findById(Long id) {
        return dispensaryRepository.findById(id);
    }

    @Override
    public Dispensary saveDispensary(Dispensary dispensary) {
        return dispensaryRepository.save(dispensary);
    }

    @Override
    public Dispensary updateDispensary(Long id, Dispensary dispensary) {
        Dispensary dispensaryDb = dispensaryRepository.findById(id).orElse(null);
        if (dispensary != null){
            dispensaryDb.setDispensayDate(dispensary.getDispensayDate());
            dispensaryDb.setPatient(dispensary.getPatient());
            dispensaryDb.setMedicine(dispensary.getMedicine());
            dispensaryDb.setQuantity(dispensary.getQuantity());
            dispensaryDb.setUser(dispensary.getUser());
            dispensaryDb.setObservation(dispensary.getObservation());
            dispensaryDb.setStatus(dispensary.getStatus());
            return dispensaryRepository.save(dispensaryDb);
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteDispensary(Long id) {
        Dispensary dispensaryDb = dispensaryRepository.findById(id).orElse(null);
        if(dispensaryDb != null && dispensaryDb.getStatus() != 0) {
            Medicine medicineDb = dispensaryDb.getMedicine();
            double newStock = medicineDb.getStock() + dispensaryDb.getQuantity();
            medicineDb.setStock(newStock >= 0 ? newStock : 0);
            medicineRepository.save(medicineDb);

            dispensaryDb.setStatus(0);
            dispensaryRepository.save(dispensaryDb);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Long countDispensary() {
        return dispensaryRepository.count();
    }

    @Override
    @Transactional
    public boolean dispensaryMedicine(Long id) {
        Dispensary dispensaryDb = dispensaryRepository.findById(id).orElse(null);
        if (dispensaryDb != null) {
                medicineRepository.reduceStock(dispensaryDb.getMedicine().getId(), dispensaryDb.getQuantity());
                return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean dispensaryMedicine(Long id, double previousQuantity) {
        Dispensary dispensaryDb = dispensaryRepository.findById(id).orElse(null);
        if (dispensaryDb != null) {
            double quantityDifference = dispensaryDb.getQuantity() - previousQuantity;

            if (quantityDifference > 0) {
                return medicineRepository.reduceStock(dispensaryDb.getMedicine().getId(), quantityDifference) > 0;
            }

            else if (quantityDifference < 0) {
                medicineRepository.increaseStock(dispensaryDb.getMedicine().getId(), Math.abs(quantityDifference));
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
                document.add(new Paragraph("Dispensary ID: " + dispensary.getId()));
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

    public ByteArrayInputStream generatePdfReportForDispensary(Long id) throws Exception {
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
            document.add(new Paragraph("ID de Dispensación: " + dispensary.getId(), normalFont));
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
    }

}
