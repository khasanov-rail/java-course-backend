package edu.java.bot.service;

import edu.java.bot.dto.request.TrackLinkRequest;
import edu.java.bot.dto.response.TrackLinkResponse;

public interface LinkTrackingService {
    TrackLinkResponse trackLink(TrackLinkRequest request);
}
