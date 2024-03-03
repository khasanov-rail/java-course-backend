package edu.java.bot.controller;

import edu.java.bot.dto.request.ChatRequest;
import edu.java.bot.dto.response.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> handleChat(@RequestBody ChatRequest request) {
        ChatResponse response = new ChatResponse("Обработан чат: " + request.getChatId());
        return ResponseEntity.ok(response);
    }
}
