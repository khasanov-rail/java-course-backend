package edu.java.bot.link;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.dto.scrapper.request.AddLinkRequest;
import edu.java.bot.dto.scrapper.request.RemoveLinkRequest;
import edu.java.bot.exceptions.api.ApiBadRequestException;
import edu.java.bot.exceptions.api.ApiNotFoundException;
import edu.java.bot.exceptions.api.ApiReAddingException;
import edu.java.bot.exceptions.api.ResourceUnavailableException;
import java.net.URI;

public class GitHubHandler extends LinkHandler {
    private final static LinkInfo LINK_INFO = LinkInfo.GITHUB;
    private final static String ERROR_MESSAGE = "Ошибка! Попробуйте позже!";

    GitHubHandler(ScrapperClient client) {
        super(client);
    }

    @Override
    public String subscribe(long tgChatId, URI uri) {
        String message;

        if (LINK_INFO.getHost().equals(uri.getHost())) {
            try {
                message = String.format(
                    "Ссылка %s успешно добавлена.",
                    client.addLink(tgChatId, new AddLinkRequest(uri)).getBody().url()
                );
            } catch (ApiReAddingException | ApiNotFoundException e) {
                message = e.getApiErrorResponse().description();
            } catch (ApiBadRequestException | ResourceUnavailableException e) {
                message = ERROR_MESSAGE;
            }

            return message;
        } else {
            return super.subscribe(tgChatId, uri);
        }
    }

    @Override
    public String unsubscribe(long tgChatId, URI uri) {
        String message;

        if (LINK_INFO.getHost().equals(uri.getHost())) {
            try {
                message = String.format(
                    "Ссылка %s успешно удалена.",
                    client.removeLink(tgChatId, new RemoveLinkRequest(uri)).getBody().url()
                );
            } catch (ApiNotFoundException e) {
                message = e.getApiErrorResponse().description();
            } catch (ApiBadRequestException | ResourceUnavailableException e) {
                message = ERROR_MESSAGE;
            }

            return message;
        } else {
            return super.unsubscribe(tgChatId, uri);
        }
    }
}
