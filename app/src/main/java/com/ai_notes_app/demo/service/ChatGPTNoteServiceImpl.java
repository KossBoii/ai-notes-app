package com.ai_notes_app.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatGPTNoteServiceImpl implements ChatGPTNoteService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public String generateNoteContent(String prompt, String model) {
        String url = "https://api.openai.com/v1/chat/completions"; // URL for chat completions

        // Create request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Create request body
        String requestBody = String.format(
            "{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"max_tokens\": 100}", model, prompt
        );

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // Make the API call
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                    return jsonResponse.path("choices").get(0).path("message").path("content").asText();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse response from ChatGPT", e);
                }
            } else {
                throw new RuntimeException("Failed to generate note content");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate note content", e);
        }
    }
}
