package com.my.company.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;


@Service
public class OpenAIService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String apiKey;

    public OpenAIService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"))
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
//    private final String apiKeys = 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;
//
//    public OpenAIService() {
//        this.webClient = WebClient.builder()
//                .baseUrl("https://api.openai.com/v1/chat/completions")
//                .defaultHeader("Authorization", "Bearer sk-"+ apiKeys)
//                .defaultHeader("Content-Type", "application/json")
//                .build();
    }

    public String getDefinitionInMalagasy(String word) {
        String prompt = "Hazavao amin'ny teny malagasy ny dikan'ny teny hoe: " + word;

        Map<String, Object> request = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", new Object[]{
                        Map.of("role", "user", "content", prompt)
                },
                "temperature", 0.7
        );

        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var choices = (java.util.List<Map<String, Object>>) response.get("choices");
                    return choices.get(0).get("message").toString();
                })
                .onErrorReturn("Tsy afaka namaly tamin'izao fotoana izao.")
                .block();
    }
}
