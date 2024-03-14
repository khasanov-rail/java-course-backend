package edu.java.bot.controller;

import edu.java.bot.dto.request.NotificationSettingRequest;
import edu.java.bot.dto.response.NotificationSettingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @PostMapping("/notifications/settings")
    public ResponseEntity<NotificationSettingResponse>
    updateNotificationSettings(@RequestBody NotificationSettingRequest request) {
        // Здесь будет логика настройки уведомлений
        return ResponseEntity.ok(new NotificationSettingResponse("Notifications settings updated"));
    }
}
