package edu.java.scrapper.client;

import edu.java.scrapper.dto.bot.LinkUpdateResponse;

public interface BotClient {
    void sendUpdate(LinkUpdateResponse linkUpdateRequest);
}
