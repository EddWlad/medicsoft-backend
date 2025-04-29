package com.uisrael.medical_service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@Slf4j
public class ApiAdapter {

    private final RestTemplate restTemplate;
    private String authToken;

    public ApiAdapter() {
        this.restTemplate = new RestTemplate();
    }

    public void setAuthToken(String token) {
        this.authToken = token;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(MediaType.parseMediaTypes("*/*"));

        if (authToken != null && !authToken.isEmpty()) {
            headers.set("Authorization", "Bearer " + authToken);
        }

        return headers;
    }

    public <T> T get(String url, Map<String, String> queryParams, Class<T> responseType) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            if (queryParams != null) {
                queryParams.forEach(builder::queryParam);
            }

            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
            ResponseEntity<T> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    responseType
            );
            return response.getBody();
        } catch (Exception e) {
            handleError(e);
            throw e;
        }
    }

    public <T> T post(String url, Object body, Class<T> responseType) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, buildHeaders());
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    responseType
            );
            return response.getBody();
        } catch (Exception e) {
            handleError(e);
            throw e;
        }
    }

    public <T> T put(String url, Object body, Class<T> responseType) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, buildHeaders());
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    responseType
            );
            return response.getBody();
        } catch (Exception e) {
            handleError(e);
            throw e;
        }
    }

    public <T> T delete(String url, Map<String, String> queryParams, Class<T> responseType) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            if (queryParams != null) {
                queryParams.forEach(builder::queryParam);
            }

            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
            ResponseEntity<T> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.DELETE,
                    entity,
                    responseType
            );
            return response.getBody();
        } catch (Exception e) {
            handleError(e);
            throw e;
        }
    }

    private void handleError(Exception e) {
        log.error("Error al consumir API externa: {}", e.getMessage());

    }
}