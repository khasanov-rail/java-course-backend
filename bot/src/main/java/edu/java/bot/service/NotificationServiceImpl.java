package edu.java.bot.service;

import edu.java.bot.dto.request.NotificationSettingRequest;
import edu.java.bot.dto.response.NotificationSettingResponse;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public NotificationSettingResponse updateNotificationSettings(NotificationSettingRequest request) {
        // Здесь реализация обновления настроек уведомлений
        // Например, обновление в БД (пропущено для упрощения)
        return new NotificationSettingResponse("Notification settings updated to: " + request.isEnableNotifications());
    }
}
