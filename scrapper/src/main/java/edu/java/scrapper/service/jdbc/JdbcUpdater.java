package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.repositoty.JdbcLinksRepository;
import edu.java.scrapper.dto.bot.LinkUpdateResponse;
import edu.java.scrapper.dto.github.GitHubDTO;
import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import edu.java.scrapper.service.LinkUpdater;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcUpdater implements LinkUpdater {
    private final JdbcLinksRepository linkRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;

    private static final Duration UPDATE_TIME = Duration.ofSeconds(1);

    @Override
    public void update() {
        OffsetDateTime time = OffsetDateTime.now().minus(UPDATE_TIME);
        List<Link> linksToCheck = linkRepository.findLinksToCheck(time);
        for (Link link : linksToCheck) {
            if (link.getUrl().toString().contains("github.com")) {
                gitHibProcess(link);
            } else if (link.getUrl().toString().contains("stackoverflow.com")) {
                stackOverflowProcess(link);
            }
            linkRepository.updateCheckAt(time, link.getId());
        }
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private void gitHibProcess(Link link) {
        String[] parts = link.getUrl().toString().split("/");
        List<GitHubDTO> repo;
        try {
            repo = gitHubClient.fetchRepo(parts[3], parts[4]);
        } catch (Exception e) {
            return;
        }

        for (GitHubDTO event : repo) {
            if (event.createdAt().isAfter(link.getCheckedAt())) {
                String message = gitHubClient.getMessage(event);
                if (!message.isEmpty()) {
                    botClient.sendUpdate(
                        new LinkUpdateResponse(
                            link.getId(),
                            link.getUrl(),
                            message,
                            linkRepository.tgChatIdsByLinkId(link.getId())
                        ));
                }
            } else {
                break;
            }
        }
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private void stackOverflowProcess(Link link) {
        Long questionId = Long.parseLong(link.getUrl().toString().split("/")[4]);
        StackOverflowDTO answers;
        try {
            answers = stackOverflowClient.fetchAnswersByQuestionId(questionId);
        } catch (Exception e) {
            return;
        }

        for (StackOverflowDTO.Item event : answers.items()) {
            if (event.creationDate().isAfter(link.getCheckedAt())) {
                String message = stackOverflowClient.getMessage(event);
                if (!message.isEmpty()) {
                    botClient.sendUpdate(
                        new LinkUpdateResponse(
                            link.getId(),
                            link.getUrl(),
                            stackOverflowClient.getMessage(event),
                            linkRepository.tgChatIdsByLinkId(link.getId())
                        ));
                }
            } else {
                break;
            }
        }
    }

}


