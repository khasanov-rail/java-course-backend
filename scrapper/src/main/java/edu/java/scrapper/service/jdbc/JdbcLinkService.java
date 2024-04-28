package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.exception.custom.ChatIdNotFoundException;
import edu.java.scrapper.exception.custom.LinkNotFoundException;
import edu.java.scrapper.exception.custom.ReAddingLinkException;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repositoty.jdbc.JdbcChatsRepository;
import edu.java.scrapper.repositoty.jdbc.JdbcLinksRepository;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcChatsRepository chatsRepository;
    private final JdbcLinksRepository linksRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        String urlStr = url.toString();
        checkChatExists(tgChatId);
        if (linksRepository.isLinkAlreadyAddedForChat(urlStr, tgChatId)) {
            throw new ReAddingLinkException(String.format("Ссылка с URL %s уже добавлена для данного чата", urlStr));
        }

        Optional<Link> linkOptional = linksRepository.findLinkByUrl(urlStr);
        if (linkOptional.isEmpty()) {
            linksRepository.add(urlStr);
        }
        long linkId = linksRepository.findLinkByUrl(urlStr).get().getId();
        linksRepository.addRelationship(linkId, tgChatId);
        return linksRepository.findById(linkId);
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        checkChatExists(tgChatId);

        Optional<Link> linkOptional = linksRepository.findLinkByUrl(url.toString());
        if (linkOptional.isEmpty()) {
            throw new LinkNotFoundException(String.format("Ссылка с URL %s не найдена", url));
        }

        long linkId = linkOptional.get().getId();
        Link deletedLink = linksRepository.findById(linkId);
        linksRepository.remove(tgChatId, linkId);
        return deletedLink;
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        checkChatExists(tgChatId);
        return linksRepository.findAllByChatId(tgChatId);
    }

    private void checkChatExists(long tgChatId) {
        if (chatsRepository.findChatById(tgChatId).isEmpty()) {
            throw new ChatIdNotFoundException(String.format("Чат с id %d не найден", tgChatId));
        }
    }
}
