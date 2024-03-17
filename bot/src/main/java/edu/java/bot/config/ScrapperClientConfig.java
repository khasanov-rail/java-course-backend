package edu.java.bot.config;

import edu.java.bot.service.ScrapperClient;
import edu.java.bot.service.ScrapperClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ScrapperClientConfig {

    @Bean
    public ScrapperClient scrapperClient(WebClient webClient) {
        return new ScrapperClientImpl(webClient);
    }
}
