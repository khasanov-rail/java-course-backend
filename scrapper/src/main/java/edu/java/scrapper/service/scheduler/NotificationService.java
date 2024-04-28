package edu.java.scrapper.service.scheduler;

import edu.java.scrapper.dto.bot.LinkUpdateResponse;

public interface NotificationService {
    void sendNotification(LinkUpdateResponse update);
}
