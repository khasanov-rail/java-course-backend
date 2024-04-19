package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.repositoty.JdbcChatsRepository;
import edu.java.scrapper.dto.api.request.AddChatRequest;
import edu.java.scrapper.exception.custom.ChatIdNotFoundException;
import edu.java.scrapper.exception.custom.ReRegistrationException;
import edu.java.scrapper.model.Chat;
import edu.java.scrapper.service.TgChatService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final JdbcChatsRepository chatsRepository;

    @Override
    public void register(long tgChatId, AddChatRequest addChatRequest) {
        Optional<Chat> foundChat = chatsRepository.findChatById(tgChatId);
        if (foundChat.isPresent()) {
            throw new ReRegistrationException(String.format("Чат с ID %d уже добавлен", tgChatId));
        }

        chatsRepository.add(tgChatId, addChatRequest.userName());
    }

    @Override
    public void unregister(long tgChatId) {
        checkChatExists(tgChatId);
        chatsRepository.remove(tgChatId);
    }

    @Override
    public void checkChatExists(long tgChatId) {
        Optional<Chat> existingChat = chatsRepository.findChatById(tgChatId);
        if (existingChat.isEmpty()) {
            throw new ChatIdNotFoundException(String.format("Чат с ID %d не найден", tgChatId));
        }
    }
}
