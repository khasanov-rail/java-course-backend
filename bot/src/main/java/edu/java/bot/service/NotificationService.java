package edu.java.bot.service;

import edu.java.bot.dto.request.NotificationSettingRequest;
import edu.java.bot.dto.response.NotificationSettingResponse;

public interface NotificationService {
    NotificationSettingResponse updateNotificationSettings(NotificationSettingRequest request);
}
