package edu.java.bot.service;

import edu.java.bot.dto.request.NotificationSettingRequest;
import edu.java.bot.dto.response.NotificationSettingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotificationServiceTest {

    private NotificationService notificationService = new NotificationServiceImpl();

    @Test
    @DisplayName("Обновление настроек уведомлений работает корректно")
    void testUpdateNotificationSettings() {
        // Arrange
        NotificationSettingRequest request = new NotificationSettingRequest();
        request.setEnableNotifications(true);

        // Act
        NotificationSettingResponse response = notificationService.updateNotificationSettings(request);

        // Assert
        assertNotNull(response);
        assertEquals("Notification settings updated to: true", response.getMessage());
    }
}

