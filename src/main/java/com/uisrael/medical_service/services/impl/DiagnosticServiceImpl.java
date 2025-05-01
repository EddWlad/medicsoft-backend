package com.uisrael.medical_service.services.impl;

import com.uisrael.medical_service.entities.Diagnostic;
import com.uisrael.medical_service.dtos.DiagnosticDTO;
import com.uisrael.medical_service.repositories.IDiagnosticRepository;
import com.uisrael.medical_service.repositories.IGenericRepository;
import com.uisrael.medical_service.services.IDiagnosticService;
import groovyjarjarpicocli.CommandLine;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiagnosticServiceImpl extends GenericServiceImpl<Diagnostic, UUID> implements IDiagnosticService {

    private final IDiagnosticRepository diagnosticRepository;
    /*private final ChatClient chatClient;*/

    @Override
    protected IGenericRepository<Diagnostic, UUID> getRepo() {
        return diagnosticRepository;
    }


    @Override
    public Long countDiagnostic() {
        return diagnosticRepository.count();
    }

    /*@Override
    public String generateDiagnosticFromSymptoms(String symptoms) {
        String responseDiagnostic =  "Simula que eres un experto médico general y con los síntomas proporcionados, " +
                "da un diagnóstico lo más exacto posible sugiriendo algun medicamento que puede tener un medico ocupacional a la mano," +
                "tambien tu respuesta debera ser de 3 a 4 lineas" +
                "tengo los siguientes sintomas: "+symptoms;

        return chatClient.prompt(responseDiagnostic).call().content().toString();
    }

    @Override
    public List<DiagnosticDTO> findDiagnosticsByPatientNameAndLastName(String name, String lastName) {
        List<Diagnostic> diagnostics = diagnosticRepository.findByPatientNameAndLastName(name, lastName);
        return diagnostics.stream()
                .map(diagnostic -> {
                    DiagnosticDTO dto = new DiagnosticDTO();
                    dto.setIdDiagnostic(diagnostic.getIdDiagnostic());
                    dto.setPatient(diagnostic.getPatient());
                    dto.setSymptoms(diagnostic.getSymptoms());
                    dto.setDiagnostic(diagnostic.getDiagnostic());
                    dto.setObservation(diagnostic.getObservation());
                    dto.setDiagnosticDate(diagnostic.getDiagnosticDate());
                    dto.setStatus(diagnostic.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void markAllAsSeenByPatientId(UUID idPatient) {
        List<Diagnostic> diagnostics = diagnosticRepository.findByPatientIdAndIsNewTrue(idPatient);
        for (Diagnostic diagnostic : diagnostics) {
            diagnostic.setIsNew(false);
            //diagnosticRepository.save(diagnostic);
        }
        diagnosticRepository.saveAll(diagnostics);
    }*/
}
