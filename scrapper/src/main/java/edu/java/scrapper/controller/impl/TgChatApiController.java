package edu.java.scrapper.controller.impl;

import edu.java.scrapper.controller.TgChatApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TgChatApiController implements TgChatApi {
    @Override
    public ResponseEntity<Void> removeChat(Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addChat(Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
