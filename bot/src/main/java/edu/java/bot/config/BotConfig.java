package edu.java.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {
    @Value("${app.telegram-token}")
    private String token;

    public String getToken() {
        return token;
    }
}
