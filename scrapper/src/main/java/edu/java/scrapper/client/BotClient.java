package edu.java.scrapper.client;

import edu.java.scrapper.dto.bot.LinkUpdateResponse;
import org.springframework.http.ResponseEntity;

public interface BotClient {
    ResponseEntity<Void> sendUpdate(LinkUpdateResponse linkUpdateRequest);
}
