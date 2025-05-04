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

    @Override
    protected IGenericRepository<Diagnostic, UUID> getRepo() {
        return diagnosticRepository;
    }

    @Override
    public Long countDiagnostic() {
        return diagnosticRepository.count();
    }


}
