package com.uisrael.medical_service.controllers;

import com.uisrael.medical_service.dtos.AIDiagnosticDTO;
import com.uisrael.medical_service.dtos.ResponseDTO;
import com.uisrael.medical_service.utils.ChatHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatAIController {
    private final OpenAiChatModel openAiChatModel;
    private final ChatHistory chatHistory;

    @GetMapping
    public ResponseEntity<ResponseDTO<String>> generateDiagnostic(@RequestParam String symptoms){
        ChatResponse chatResponse = openAiChatModel.call(new Prompt(symptoms));
        String result = chatResponse.getResult().getOutput().getText();
        return ResponseEntity.ok(new ResponseDTO<>(200, "success", result));
    }

    @GetMapping("/prompt")
    public ResponseEntity<ResponseDTO<String>> generatePrompt(@RequestParam String symptoms, @RequestParam String observations){
        PromptTemplate promptTemplate = new PromptTemplate("Simula que eres un excelente doctor asistente que daras ayuda a una doctora ocupacional de la empresa EBICS SA" +
                "dame un diagnostico lo mas exacto posible con estos sintomas {symptoms}" +
                "tomando encuenta algunas de estas observaciones {observations}, sugiere uno o varios medicamentos sencillo que tendria un doctor ocupacional de la empresa a la mano" +
                "siempre sugiere que llames al doctor ocupacional para que estes seguro y solo imprime 400 caracteres");
        Prompt prompt = promptTemplate.create(Map.of("symptoms", symptoms, "observations", observations));

        ChatResponse chatResponse = openAiChatModel.call(prompt);
        String result = chatResponse.getResult().getOutput().getText();

        return ResponseEntity.ok(new ResponseDTO<>(200, "success", result));
    }

    @GetMapping("/output")
    public ResponseEntity<AIDiagnosticDTO> generateOutputConverter(@RequestParam String symptoms){
       BeanOutputConverter<AIDiagnosticDTO> outputConverter = new BeanOutputConverter<>(AIDiagnosticDTO.class);

       String template = """
               Simula que eres un excelente doctor asistente que daras ayuda a una doctora ocupacional de la empresa EBICS SA
               dame un diagnostico lo mas exacto posible con estos sintomas {symptoms} .{format}, sugiere uno o varios medicamentos sencillo que tendria un doctor ocupacional de la empresa a la mano
               siempre sugiere que llames al doctor ocupacional para que estes seguro y solo imprime 400 caracteres
               """;
       PromptTemplate promptTemplate = new PromptTemplate(template);
       Prompt prompt = promptTemplate.create(Map.of("symptoms", symptoms, "format", outputConverter.getFormat()));

        ChatResponse chatResponse = openAiChatModel.call(prompt);
        String result = chatResponse.getResult().getOutput().getText();

        AIDiagnosticDTO aiDiagnosticDTO = outputConverter.convert(result);

        return ResponseEntity.ok(aiDiagnosticDTO);
    }

    @GetMapping("/conversations")
    public ResponseEntity<ResponseDTO<String>> generateConveration(@RequestParam String symptoms){

        chatHistory.addMessage("1", new UserMessage(symptoms));

        ChatResponse chatResponse = openAiChatModel.call(new Prompt(chatHistory.getAll("1")));
        String result = chatResponse.getResult().getOutput().getText();

        return ResponseEntity.ok(new ResponseDTO<>(200, "success", result));
    }

}
