package edu.java.bot.link;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.dto.scrapper.request.AddLinkRequest;
import edu.java.bot.dto.scrapper.request.RemoveLinkRequest;
import edu.java.bot.exceptions.api.ApiBadRequestException;
import edu.java.bot.exceptions.api.ApiNotFoundException;
import edu.java.bot.exceptions.api.ApiReAddingException;
import java.net.URI;

public class StackOverflowHandler extends LinkHandler {
    private final static LinkInfo RESOURCE = LinkInfo.STACKOVERFLOW;
    private final static String ERROR_MESSAGE = "Ошибка! Попробуйте позже!";

    StackOverflowHandler(ScrapperClient client) {
        super(client);
    }

    @Override
    public String subscribe(long tgChatId, URI uri) {
        String message;

        if (uri.getHost().equals(RESOURCE.getHost())) {
            try {
                message = String.format(
                    "Ссылка %s успешно добавлена.",
                    client.addLink(tgChatId, new AddLinkRequest(uri)).getBody().url()
                );
            } catch (ApiReAddingException | ApiNotFoundException e) {
                message = e.getApiErrorResponse().description();
            } catch (ApiBadRequestException e) {
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

        if (uri.getHost().equals(RESOURCE.getHost())) {
            try {
                message = String.format(
                    "Ссылка %s успешно удалена.",
                    client.removeLink(tgChatId, new RemoveLinkRequest(uri)).getBody().url()
                );
            } catch (ApiNotFoundException e) {
                message = e.getApiErrorResponse().description();
            } catch (ApiBadRequestException e) {
                message = ERROR_MESSAGE;
            }

            return message;
        } else {
            return super.unsubscribe(tgChatId, uri);
        }
    }
}
