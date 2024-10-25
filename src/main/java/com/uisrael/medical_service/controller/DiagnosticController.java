package com.uisrael.medical_service.controller;

import com.uisrael.medical_service.entities.Diagnostic;
import com.uisrael.medical_service.model.DiagnosticDTO;
import com.uisrael.medical_service.service.IDiagnosticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diagnostic")
public class DiagnosticController {
    @Autowired
    private IDiagnosticService diagnosticService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<DiagnosticDTO> diagnosticList = diagnosticService.getAll()
                .stream()
                .map(diagnostic -> DiagnosticDTO.builder()
                .id(diagnostic.getId())
                .diagnostic(diagnostic.getDiagnostic())
                .diagnosticDate(diagnostic.getDiagnosticDate())
                .observation(diagnostic.getObservation())
                .symptoms(diagnostic.getSymptoms())
                .patient(diagnostic.getPatient())
                .status(diagnostic.getStatus())
                .build())
                .toList();
        return ResponseEntity.ok(diagnosticList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id)
    {
        Optional<Diagnostic> diagnosticOptional = diagnosticService.findById(id);
        if(diagnosticOptional.isPresent()){
            Diagnostic diagnostic = diagnosticOptional.get();
            DiagnosticDTO diagnosticDTO = DiagnosticDTO.builder()
                    .id(diagnostic.getId())
                    .diagnostic(diagnostic.getDiagnostic())
                    .diagnosticDate(diagnostic.getDiagnosticDate())
                    .observation(diagnostic.getObservation())
                    .symptoms(diagnostic.getSymptoms())
                    .patient(diagnostic.getPatient())
                    .status(diagnostic.getStatus())
                    .build();
            return ResponseEntity.ok(diagnosticDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/diagnostic")
    public ResponseEntity<String> diagnostic(@RequestParam String symptoms){
        if(symptoms == null || symptoms.isBlank()) {
            return ResponseEntity.badRequest().body("No existen síntomas válidos.");
        }
        String diagnosticResult = diagnosticService.generateDiagnosticFromSymptoms(symptoms);
        return ResponseEntity.ok(diagnosticResult);
    }

    @PostMapping(value = "/save",consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody DiagnosticDTO diagnosticDTO) throws URISyntaxException {
        if(diagnosticDTO.getDiagnostic().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        diagnosticService.saveDiagnostic(Diagnostic.builder()
                .id(diagnosticDTO.getId())
                .diagnosticDate(diagnosticDTO.getDiagnosticDate())
                .patient(diagnosticDTO.getPatient())
                .symptoms(diagnosticDTO.getSymptoms())
                .diagnostic(diagnosticDTO.getDiagnostic())
                .observation(diagnosticDTO.getObservation())
                .status(diagnosticDTO.getStatus())
                .build());

        return ResponseEntity.created(new URI("/api/diagnostic/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDiagnostic(@PathVariable Long id, @RequestBody DiagnosticDTO diagnosticDTO ){
        Optional<Diagnostic> diagnosticOptional = diagnosticService.findById(id);
        if(diagnosticOptional.isPresent()){
            Diagnostic diagnostic = diagnosticOptional.get();
            diagnostic.setDiagnostic(diagnosticDTO.getDiagnostic());
            diagnostic.setDiagnosticDate(diagnosticDTO.getDiagnosticDate());
            diagnostic.setObservation(diagnosticDTO.getObservation());
            diagnostic.setSymptoms(diagnosticDTO.getSymptoms());
            diagnostic.setPatient(diagnosticDTO.getPatient());
            diagnostic.setStatus(diagnosticDTO.getStatus());
            diagnosticService.updateDiagnostic(id,diagnostic);
            return  ResponseEntity.ok("Diagnostico Actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDiagnostic(@PathVariable Long id){
        boolean result = diagnosticService.deleteDiagnostic(id);
        if(result){
            return ResponseEntity.ok("Diagnostico eliminado");
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
