package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.repositoty.JdbcLinksRepository;
import edu.java.scrapper.exception.custom.LinkNotFoundException;
import edu.java.scrapper.exception.custom.ReAddingLinkException;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.TgChatService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinksRepository linksRepository;

    private final TgChatService jdbcTgChatService;

    @Override
    public Link add(long tgChatId, URI url) {
        String urlStr = url.toString();

        jdbcTgChatService.checkChatExists(tgChatId);
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
        jdbcTgChatService.checkChatExists(tgChatId);

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
        jdbcTgChatService.checkChatExists(tgChatId);
        return linksRepository.findAllByChatId(tgChatId);
    }
}
