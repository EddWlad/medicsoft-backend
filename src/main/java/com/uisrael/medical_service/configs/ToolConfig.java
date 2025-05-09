package com.uisrael.medical_service.configs;

import com.uisrael.medical_service.repositories.IMedicineRepository;
import com.uisrael.medical_service.services.impl.AIMedicineServiceImpl;

import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {
    @Bean
    public ToolCallback getMedicineStock(IMedicineRepository medicineRepository){
        return FunctionToolCallback.builder("MedicineInfo", new AIMedicineServiceImpl(medicineRepository))
                .description("Obtener informacion de medicina desde el nombre de la medicina")
                .inputType(AIMedicineServiceImpl.Request.class)
                .build();
    }
}
