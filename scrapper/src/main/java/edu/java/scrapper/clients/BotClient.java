package edu.java.scrapper.clients;

import edu.java.scrapper.dto.BotResponse; // DTO для ответа от bot модуля
import reactor.core.publisher.Mono;

public interface BotClient {
    Mono<BotResponse> sendNotification(String chatId, String message);
}
