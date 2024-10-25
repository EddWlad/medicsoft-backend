package com.uisrael.medical_service.controller;

import com.uisrael.medical_service.entities.Diagnostic;
import com.uisrael.medical_service.entities.Dispensary;
import com.uisrael.medical_service.entities.Patient;
import com.uisrael.medical_service.model.PatientDTO;
import com.uisrael.medical_service.model.UserDTO;
import com.uisrael.medical_service.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/patient")
public class PatientController {
    @Autowired
    private IPatientService patientService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<PatientDTO> patientList = patientService.getAll()
                .stream()
                .map(patient -> PatientDTO.builder()
                        .id(patient.getId())
                        .name(patient.getName())
                        .lastName(patient.getLastName())
                        .department(patient.getDepartment())
                        .gender(patient.getGender())
                        .year(patient.getYear())
                        .weight(patient.getWeight())
                        .size(patient.getSize())
                        .observation(patient.getObservation())
                        .status(patient.getStatus())
                        .build())
                .toList();
        return ResponseEntity.ok(patientList);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Patient> patientOptional = patientService.findById(id);
        if(patientOptional.isPresent()){
            Patient patient = patientOptional.get();
            PatientDTO patientDTO = PatientDTO.builder()
                    .id(patient.getId())
                    .name(patient.getName())
                    .lastName(patient.getLastName())
                    .department(patient.getDepartment())
                    .gender(patient.getGender())
                    .year(patient.getYear())
                    .weight(patient.getWeight())
                    .size(patient.getSize())
                    .observation(patient.getObservation())
                    .status(patient.getStatus())
                    .build();
            return ResponseEntity.ok(patientDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePatient(@RequestBody PatientDTO patientDTO) throws URISyntaxException {
        if(patientDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        patientService.savePatient(Patient.builder()
                .id(patientDTO.getId())
                .name(patientDTO.getName())
                .lastName(patientDTO.getLastName())
                .department(patientDTO.getDepartment())
                .gender(patientDTO.getGender())
                .year(patientDTO.getYear())
                .weight(patientDTO.getWeight())
                .size(patientDTO.getSize())
                .observation(patientDTO.getObservation())
                .status(patientDTO.getStatus())
                .build());
        return ResponseEntity.created(new URI("/api/patient/save")).build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO ){
        Optional<Patient> patientOptional = patientService.findById(id);
        if(patientOptional.isPresent()){
            Patient patient = patientOptional.get();
            patient.setName(patientDTO.getName());
            patient.setLastName(patientDTO.getLastName());
            patient.setDepartment(patientDTO.getDepartment());
            patient.setGender(patientDTO.getGender());
            patient.setYear(patientDTO.getYear());
            patient.setWeight(patientDTO.getWeight());
            patient.setSize(patientDTO.getSize());
            patient.setObservation(patientDTO.getObservation());
            patient.setStatus(patientDTO.getStatus());
            patientService.updatePatient(id, patient);
            return ResponseEntity.ok("Paciente actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id){
        boolean result = patientService.deletePatient(id);
        if(result){
            return ResponseEntity.ok("Paciente eliminado");
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
