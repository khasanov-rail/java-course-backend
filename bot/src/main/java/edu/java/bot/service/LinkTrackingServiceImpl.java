package edu.java.bot.service;

import edu.java.bot.dto.request.TrackLinkRequest;
import edu.java.bot.dto.response.TrackLinkResponse;
import org.springframework.stereotype.Service;

@Service
public class LinkTrackingServiceImpl implements LinkTrackingService {

    @Override
    public TrackLinkResponse trackLink(TrackLinkRequest request) {
        // Здесь реализация добавления ссылки для отслеживания
        // Например, сохранение в БД (пропущено для упрощения)
        return new TrackLinkResponse("Successfully started tracking link: " + request.getLink());
    }
}
