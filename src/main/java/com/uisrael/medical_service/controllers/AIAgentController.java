package com.uisrael.medical_service.controllers;

import com.uisrael.medical_service.dtos.ResponseDTO;
import com.uisrael.medical_service.services.IMedicineService;
import com.uisrael.medical_service.utils.ChatHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agents")
@RequiredArgsConstructor
public class AIAgentController {
    private final OpenAiChatModel openAiChatModel;
    private final IMedicineService medicineService;
    private final ChatHistory chatHistory;

    @GetMapping
    public ResponseEntity<String> getMedicineStock(@RequestParam("medicine") String medicine){
        UserMessage userMessage = new UserMessage("Cual es la informacion de este medicamento" + medicine + "en el consultorio?");
        ChatResponse response = openAiChatModel.call(new Prompt(List.of(userMessage),
                OpenAiChatOptions.builder().toolNames("MedicineInfo").build()
                ));

        String result = response.getResult().getOutput().getText();

        return  ResponseEntity.ok(result);
    }

    @GetMapping("/agent")
    public ResponseEntity<ResponseDTO<String>> generateAgentResponse(@RequestParam String symptoms) {
        UserMessage userMessage = new UserMessage("""
        Eres un médico asistente para la empresa EBICS SA. 
        Basado en los síntomas proporcionados, realiza un diagnostico lo mas exacto posible con el PADECIMIENTO o ENFERMEDAD que podria tener pon enfasis en esto da un diagnostico y sugiere solo medicamentos que se encuentran disponibles en el consultorio y puedan aliviar el padecimiento o enfermedad. 
        Utiliza la herramienta "MedicineInfo" para verificar si el medicamento está disponible.
        Utiliza la herramienta "MedicineInfo" para verificar la descripcion del medicamento que por lo general indica para que dolor ayuda el alivio.
        Una vez que veas todos los medicamentos revisa en tus conocimientos medicos cual le puede sugerir, recuerda que eres un experto doctor ocupacional.
        No muestres el stock ni datos técnicos. 
        Responde de manera empática y profesional, sugiere tambien tratmientos caseros de ser posible. 
        No superes los 400 caracteres. 
        Cuando hayas hecho todos estos pasos, indica que de click en el boton enviar, para que el diagnostico se guarde y se procesada ha informar a la doctora para que te de apertura al consultorio medico para que recogas tu medicamento pero si solo hay en stock.
        Indica al paciente que contacte a la doctora para mayor seguridad. 
        Síntomas: """ + symptoms);

        Prompt prompt = new Prompt(
                List.of(userMessage),
                OpenAiChatOptions.builder()
                        .toolNames("MedicineInfo")
                        .build()
        );

        ChatResponse response = openAiChatModel.call(prompt);
        String result = response.getResult().getOutput().getText();

        return ResponseEntity.ok(new ResponseDTO<>(200, "success", result));
    }

}
