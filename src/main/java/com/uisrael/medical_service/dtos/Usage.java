package com.uisrael.medical_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usage {
    private List<PromptTokenDetails> prompt_tokens_details;

    @Data
    public static class PromptTokenDetails {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }
}
