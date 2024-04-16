package edu.java.scrapper.controller.impl;

import edu.java.scrapper.controller.TgChatApi;
import edu.java.scrapper.dto.api.request.AddChatRequest;
import edu.java.scrapper.service.TgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TgChatApiController implements TgChatApi {
    private final TgChatService tgChatService;

    @Override
    public ResponseEntity<Void> removeChat(Long tgChatId) {
        tgChatService.unregister(tgChatId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addChat(Long tgChatId, AddChatRequest addChatRequest) {
        tgChatService.register(tgChatId, addChatRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
