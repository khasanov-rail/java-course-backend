package edu.java.bot.service;

import edu.java.bot.dto.request.GitHubInfoRequest;
import edu.java.bot.dto.request.StackOverflowInfoRequest;
import edu.java.bot.dto.response.GitHubInfoResponse;
import edu.java.bot.dto.response.StackOverflowInfoResponse;
import reactor.core.publisher.Mono;

public interface ScrapperClient {
    Mono<GitHubInfoResponse> fetchGitHubInfo(GitHubInfoRequest request);

    Mono<StackOverflowInfoResponse> fetchStackOverflowInfo(StackOverflowInfoRequest request);
}

