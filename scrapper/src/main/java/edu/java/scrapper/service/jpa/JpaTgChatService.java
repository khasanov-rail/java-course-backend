package edu.java.scrapper.service.jpa;

import edu.java.scrapper.dto.api.request.AddChatRequest;
import edu.java.scrapper.exception.custom.ChatIdNotFoundException;
import edu.java.scrapper.exception.custom.ReRegistrationException;
import edu.java.scrapper.model.Chat;
import edu.java.scrapper.repositoty.jpa.JpaChatRepository;
import edu.java.scrapper.repositoty.jpa.JpaLinkRepository;
import edu.java.scrapper.service.TgChatService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;

    @Override
    public void register(long tgChatId, AddChatRequest addChatRequest) {
        jpaChatRepository.findById(tgChatId).ifPresent(c -> {
            throw new ReRegistrationException(String.format("Чат с id %d уже добавлен", tgChatId));
        });

        Chat chat = new Chat(tgChatId, addChatRequest.userName());
        jpaChatRepository.save(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        Chat chat = jpaChatRepository.findById(tgChatId)
            .orElseThrow(() -> new ChatIdNotFoundException(String.format("Чат с id %d не найден", tgChatId)));

        chat.getLinks().forEach(link -> {
            if (link.getChats().size() == 1) {
                jpaLinkRepository.delete(link);
            }
        });

        jpaChatRepository.deleteById(tgChatId);
    }
}
