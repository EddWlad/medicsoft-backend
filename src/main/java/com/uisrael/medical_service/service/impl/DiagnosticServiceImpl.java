package com.uisrael.medical_service.service.impl;

import com.uisrael.medical_service.entities.Diagnostic;
import com.uisrael.medical_service.repositories.IDiagnosticRepository;
import com.uisrael.medical_service.service.IDiagnosticService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiagnosticServiceImpl implements IDiagnosticService {
    @Autowired
    private IDiagnosticRepository diagnosticRepository;

    private final ChatClient chatClient;

    DiagnosticServiceImpl(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @Override
    public List<Diagnostic> getAll() {
        return diagnosticRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Diagnostic> findById(Long id) {
        return diagnosticRepository.findById(id);
    }

    @Override
    public Diagnostic saveDiagnostic(Diagnostic diagnostic) {
        return diagnosticRepository.save(diagnostic);
    }

    @Override
    public Diagnostic updateDiagnostic(Long id, Diagnostic diagnostic) {
        Diagnostic diagnosticDb = diagnosticRepository.findById(id).orElse(null);
        if(diagnostic != null)
        {
            diagnosticDb.setDiagnosticDate(diagnostic.getDiagnosticDate());
            diagnosticDb.setPatient(diagnostic.getPatient());
            diagnosticDb.setSymptoms(diagnostic.getSymptoms());
            diagnosticDb.setDiagnostic(diagnostic.getDiagnostic());
            diagnosticDb.setObservation(diagnostic.getObservation());
            diagnosticDb.setStatus(diagnostic.getStatus());
            return diagnosticRepository.save(diagnosticDb);
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean deleteDiagnostic(Long id) {
        Diagnostic diagnosticDb = diagnosticRepository.findById(id).orElse(null);
        if(diagnosticDb != null)
        {
            diagnosticDb.setStatus(0);
            diagnosticRepository.save(diagnosticDb);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public Long countDiagnostic() {
        return diagnosticRepository.count();
    }

    @Override
    public String generateDiagnosticFromSymptoms(String symptoms) {
        String responseDiagnostic =  "Simula que eres un experto médico general y con los síntomas proporcionados, " +
                "da un diagnóstico lo más exacto posible sugiriendo algun medicamento que puede tener un medico ocupacional a la mano," +
                "tambien tu respuesta debera ser de 3 a 4 lineas" +
                "tengo los siguientes sintomas: "+symptoms;
        return chatClient.prompt(responseDiagnostic).call().content().toString();
    }
}
