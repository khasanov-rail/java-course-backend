package edu.java.scrapper.scheduler;

import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.dto.github.GitHubResponse;
import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LinkUpdaterScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkUpdaterScheduler.class);
    private static final String GITHUB_OWNER = "your-github-owner";
    private static final String GITHUB_REPO_NAME = "your-repo-name";
    private static final String STACK_OVERFLOW_ORDER = "desc";
    private static final String STACK_OVERFLOW_SORT = "activity";
    private static final String STACK_OVERFLOW_SITE = "stackoverflow";

    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    public LinkUpdaterScheduler(GitHubClient gitHubClient, StackOverflowClient stackOverflowClient) {
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
    }

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        updateGitHubInfo();
        updateStackOverflowInfo();
    }

    private void updateGitHubInfo() {
        gitHubClient.fetchRepositoryInfo(GITHUB_OWNER, GITHUB_REPO_NAME)
            .subscribe(this::logGitHubResponse, throwable -> LOGGER.error("Error fetching GitHub info", throwable));
    }

    private void logGitHubResponse(GitHubResponse response) {
        LOGGER.info("GitHub Repo Info: {}", response);
    }

    private void updateStackOverflowInfo() {
        stackOverflowClient.fetchQuestions(STACK_OVERFLOW_ORDER, STACK_OVERFLOW_SORT, STACK_OVERFLOW_SITE)
            .subscribe(
                this::logStackOverflowResponse,
                throwable -> LOGGER.error("Error fetching StackOverflow info", throwable)
            );
    }

    private void logStackOverflowResponse(StackOverflowDTO response) {
        LOGGER.info("StackOverflow Questions Info: {}", response);
    }
}
