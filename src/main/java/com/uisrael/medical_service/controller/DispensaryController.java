package com.uisrael.medical_service.controller;

import com.uisrael.medical_service.entities.Dispensary;
import com.uisrael.medical_service.entities.Medicine;
import com.uisrael.medical_service.model.DispensaryDTO;
import com.uisrael.medical_service.service.IDispensaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/dispensary")
public class DispensaryController {
    @Autowired
    private IDispensaryService dispensaryService;
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<DispensaryDTO> dispensaryList = dispensaryService.getAll()
                .stream()
                .map(dispensary -> DispensaryDTO.builder()
                        .id(dispensary.getId())
                        .dispensayDate(dispensary.getDispensayDate())
                        .patient(dispensary.getPatient())
                        .medicine(dispensary.getMedicine())
                        .quantity(dispensary.getQuantity())
                        .user(dispensary.getUser())
                        .observation(dispensary.getObservation())
                        .status(dispensary.getStatus())
                        .build())
                .toList();
        return ResponseEntity.ok(dispensaryList);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Dispensary> dispensaryOptional = dispensaryService.findById(id);
        if(dispensaryOptional.isPresent()){
            Dispensary dispensary = dispensaryOptional.get();
            DispensaryDTO dispensaryDTO = DispensaryDTO.builder()
                    .id(dispensary.getId())
                    .dispensayDate(dispensary.getDispensayDate())
                    .patient(dispensary.getPatient())
                    .medicine(dispensary.getMedicine())
                    .quantity(dispensary.getQuantity())
                    .user(dispensary.getUser())
                    .observation(dispensary.getObservation())
                    .status(dispensary.getStatus())
                    .build();
            return  ResponseEntity.ok(dispensaryDTO);
        }
        return  ResponseEntity.notFound().build();
    }
    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<?> saveDispensary(@RequestBody DispensaryDTO dispensaryDTO) throws URISyntaxException {
        if (dispensaryDTO.getQuantity() <= 0) {
            return ResponseEntity.badRequest().body("La cantidad debe ser mayor a cero.");
        }

        Medicine medicine = dispensaryDTO.getMedicine();
        if (medicine.getStock() > dispensaryDTO.getQuantity()) {
            return ResponseEntity.badRequest().body("No hay suficiente stock de la medicina.");
        }

        Dispensary dispensary = Dispensary.builder()
                .id(dispensaryDTO.getId())
                .dispensayDate(dispensaryDTO.getDispensayDate())
                .patient(dispensaryDTO.getPatient())
                .medicine(dispensaryDTO.getMedicine())
                .quantity(dispensaryDTO.getQuantity())
                .user(dispensaryDTO.getUser())
                .observation(dispensaryDTO.getObservation())
                .status(dispensaryDTO.getStatus())
                .build();

        Dispensary savedDispensary = dispensaryService.saveDispensary(dispensary);

        if (savedDispensary != null && dispensaryService.dispensaryMedicine(savedDispensary.getId())) {
            return ResponseEntity.created(new URI("/api/dispensary/save")).body("Dispensación guardada y stock actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el stock del medicamento.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDispensary(@PathVariable Long id, @RequestBody DispensaryDTO dispensaryDTO) {
        Optional<Dispensary> dispensaryOptional = dispensaryService.findById(id);

        if (dispensaryOptional.isPresent()) {
            Dispensary dispensary = dispensaryOptional.get();
            double previousQuantity = dispensary.getQuantity();

            if (dispensaryDTO.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body("La cantidad debe ser mayor a cero.");
            }

            Medicine medicine = dispensaryDTO.getMedicine();
            if ( medicine.getStock() > (dispensaryDTO.getQuantity() - previousQuantity)) {
                return ResponseEntity.badRequest().body("No hay suficiente stock disponible para esta dispensación.");
            }

            dispensary.setDispensayDate(dispensaryDTO.getDispensayDate());
            dispensary.setPatient(dispensaryDTO.getPatient());
            dispensary.setMedicine(dispensaryDTO.getMedicine());
            dispensary.setQuantity(dispensaryDTO.getQuantity());
            dispensary.setUser(dispensaryDTO.getUser());
            dispensary.setObservation(dispensaryDTO.getObservation());
            dispensary.setStatus(dispensaryDTO.getStatus());

            Dispensary updatedDispensary = dispensaryService.updateDispensary(id, dispensary);

            if (updatedDispensary != null) {
                boolean stockUpdated = dispensaryService.dispensaryMedicine(updatedDispensary.getId(), previousQuantity);

                if (stockUpdated) {
                    return ResponseEntity.ok("Dispensación actualizada y stock de medicina actualizado correctamente.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el stock de la medicina.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la dispensación.");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/report/pdf")
    public ResponseEntity<byte[]> generateDispensaryReport() throws Exception {
        ByteArrayInputStream bis = dispensaryService.generatePdfReport();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=dispensary_report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

    @GetMapping("/report/pdf/{id}")
    public ResponseEntity<byte[]> generateDispensaryTicket(@PathVariable Long id) throws Exception {
        ByteArrayInputStream bis = dispensaryService.generatePdfReportForDispensary(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=dispensary_ticket_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDispensary(@PathVariable Long id){
        boolean result = dispensaryService.deleteDispensary(id);
        if(result){
            return ResponseEntity.ok("Dispensacion eliminada");
        }
        else{
            return  ResponseEntity.badRequest().build();
        }
    }
}
