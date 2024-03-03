package edu.java.scrapper.configuration;

import java.time.Duration;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

    private final Scheduler scheduler;

    public ApplicationProperties(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public static class Scheduler {
        private final boolean enable;
        private final Duration interval;
        private final Duration forceCheckDelay;

        public Scheduler(
            boolean enable,
            @NotNull Duration interval,
            @NotNull Duration forceCheckDelay
        ) {
            this.enable = enable;
            this.interval = interval;
            this.forceCheckDelay = forceCheckDelay;
        }

        public boolean isEnable() {
            return enable;
        }

        public Duration getInterval() {
            return interval;
        }

        public Duration getForceCheckDelay() {
            return forceCheckDelay;
        }
    }
}
