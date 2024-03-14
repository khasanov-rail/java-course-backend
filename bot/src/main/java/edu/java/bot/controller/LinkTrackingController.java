package edu.java.bot.controller;

import edu.java.bot.dto.request.TrackLinkRequest;
import edu.java.bot.dto.response.TrackLinkResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkTrackingController {

    @PostMapping("/track")
    public ResponseEntity<TrackLinkResponse> trackLink(@RequestBody TrackLinkRequest request) {
        // Здесь будет логика добавления ссылки для отслеживания
        return ResponseEntity.ok(new TrackLinkResponse("Link tracking initiated for: " + request.getLink()));
    }
}
