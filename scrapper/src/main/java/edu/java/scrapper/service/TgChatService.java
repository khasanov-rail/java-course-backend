package edu.java.scrapper.service;

import edu.java.scrapper.dto.api.request.AddChatRequest;

public interface TgChatService {
    void register(long tgChatId, AddChatRequest addChatRequest);

    void unregister(long tgChatId);

    void checkChatExists(long tgChatId);
}
