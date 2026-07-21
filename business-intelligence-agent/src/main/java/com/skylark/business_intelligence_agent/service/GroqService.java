package com.skylark.business_intelligence_agent.service;

import com.skylark.business_intelligence_agent.dto.groq.GroqRequest;
import com.skylark.business_intelligence_agent.dto.groq.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String askGroq(String prompt) {

        Message message = new Message("user", prompt);

        GroqRequest request = new GroqRequest(
                "llama-3.3-70b-versatile",
                List.of(message)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<GroqRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map<String, Object> body = response.getBody();

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) body.get("choices");

        Map<String, Object> choice = choices.get(0);

        Map<String, Object> messageMap =
                (Map<String, Object>) choice.get("message");

        return messageMap.get("content").toString();
    }
}