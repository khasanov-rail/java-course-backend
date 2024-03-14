package edu.java.scrapper.clients;

import edu.java.scrapper.dto.BotResponse;
import edu.java.scrapper.dto.NotificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BotClientImpl implements BotClient {

    private final WebClient webClient;

    public BotClientImpl(@Value("${api.bot.base-url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public Mono<BotResponse> sendNotification(String chatId, String message) {
        return webClient.post()
            .uri("/chat") // предполагаемый эндпоинт для отправки уведомлений
            .bodyValue(new NotificationRequest(chatId, message)) // предполагается, что существует соответствующий запрос DTO
            .retrieve()
            .bodyToMono(BotResponse.class);
    }
}
