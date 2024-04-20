package edu.java.bot.controller.impl;

import edu.java.bot.controller.UpdatesApi;
import edu.java.bot.dto.api.LinkUpdateResponse;
import edu.java.bot.service.UpdateProcessor;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateController implements UpdatesApi {
    private final UpdateProcessor updateProcessor;
    private final Counter processedUpdatesCounter;

    @Override
    public ResponseEntity<Void> sendUpdate(LinkUpdateResponse linkUpdate) {
        updateProcessor.handleUpdates(linkUpdate);

        processedUpdatesCounter.increment();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
