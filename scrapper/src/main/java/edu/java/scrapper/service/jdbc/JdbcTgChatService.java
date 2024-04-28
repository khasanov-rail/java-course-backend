package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.dto.api.request.AddChatRequest;
import edu.java.scrapper.exception.custom.ChatIdNotFoundException;
import edu.java.scrapper.exception.custom.ReRegistrationException;
import edu.java.scrapper.model.Chat;
import edu.java.scrapper.repositoty.jdbc.JdbcChatsRepository;
import edu.java.scrapper.service.TgChatService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final JdbcChatsRepository jdbcChatsRepository;

    @Override
    public void register(long tgChatId, AddChatRequest addChatRequest) {
        Optional<Chat> foundChat = jdbcChatsRepository.findChatById(tgChatId);
        if (foundChat.isPresent()) {
            throw new ReRegistrationException(String.format("Чат с id %d уже добавлен", tgChatId));
        }

        jdbcChatsRepository.add(tgChatId, addChatRequest.userName());
    }

    @Override
    public void unregister(long tgChatId) {
        if (jdbcChatsRepository.findChatById(tgChatId).isEmpty()) {
            throw new ChatIdNotFoundException(String.format("Чат с id %d не найден", tgChatId));
        }

        jdbcChatsRepository.remove(tgChatId);
    }
}
