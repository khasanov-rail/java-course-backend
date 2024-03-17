package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.request.GitHubInfoRequest;
import edu.java.bot.dto.request.StackOverflowInfoRequest;
import edu.java.bot.service.MessageService;
import edu.java.bot.service.ScrapperClient;
import reactor.core.publisher.Mono;

public class TrackHandler implements Handler {
    private final MessageService messageService;
    private final ScrapperClient scrapperClient;

    private static final int COMMAND_PARTS_MIN_REQUIRED = 3;
    private static final int COMMAND_ACTION_INDEX = 1;
    private static final int COMMAND_PARAMETER_INDEX = 2;

    public TrackHandler(MessageService messageService, ScrapperClient scrapperClient) {
        this.messageService = messageService;
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void handle(Update update) {
        String text = update.message().text();
        Long chatId = update.message().chat().id();
        String[] parts = text.split(" ", COMMAND_PARTS_MIN_REQUIRED);

        if (parts.length < COMMAND_PARTS_MIN_REQUIRED) {
            messageService.sendMessage(
                chatId,
                "Неверный формат команды. Пожалуйста, используйте /help для получения инструкций."
            );
            return;
        }

        switch (parts[COMMAND_ACTION_INDEX].toLowerCase()) {
            case "github":
                GitHubInfoRequest gitHubInfoRequest = new GitHubInfoRequest();
                String[] repoParts = parts[COMMAND_PARAMETER_INDEX].split("/");
                gitHubInfoRequest.setOwner(repoParts[0]);
                gitHubInfoRequest.setRepoName(repoParts.length > 1 ? repoParts[1] : "");
                Mono.just(scrapperClient.fetchGitHubInfo(gitHubInfoRequest))
                    .subscribe(response -> messageService.sendMessage(
                        chatId,
                        "Название: " + response.block().getFullName() + "\nОписание: "
                            +
                            response.block().getDescription()
                    ));
                break;
            case "stackoverflow":
                StackOverflowInfoRequest stackOverflowInfoRequest =
                    new StackOverflowInfoRequest(parts[COMMAND_PARAMETER_INDEX]);
                Mono.just(scrapperClient.fetchStackOverflowInfo(stackOverflowInfoRequest))
                    .subscribe(response -> messageService.sendMessage(
                        chatId,
                        "Вопросы по тегу: " + parts[COMMAND_PARAMETER_INDEX] + "\n" + response.block().toString()
                    ));
                break;
            default:
                messageService.sendMessage(
                    chatId,
                    "Невозможно отследить. Пожалуйста, используйте /help для получения инструкций."
                );
                break;
        }
    }
}
