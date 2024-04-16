package edu.java.scrapper.service.scheduler;

import edu.java.scrapper.service.LinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkUpdater linkUpdateService;

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        linkUpdateService.update();
    }
}


