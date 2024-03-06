package edu.java.scrapper.configuration;

import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.clients.GitHubClientImpl;
import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.clients.StackOverflowClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public WebClient webClientGithub(@Value("${api.github.baseUrl}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public WebClient webClientStackOverflow(@Value("${api.stackoverflow.baseUrl}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public GitHubClient gitHubClient(WebClient webClientGithub) {
        return new GitHubClientImpl(webClientGithub);
    }

    @Bean
    public StackOverflowClient stackOverflowClient(WebClient webClientStackOverflow) {
        return new StackOverflowClientImpl(webClientStackOverflow);
    }
}
