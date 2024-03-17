package edu.java.bot.service;

import edu.java.bot.dto.request.TrackLinkRequest;
import edu.java.bot.dto.response.TrackLinkResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkTrackingServiceTest {

    @InjectMocks
    private LinkTrackingService linkTrackingService = new LinkTrackingServiceImpl();

    @Test
    @DisplayName("Отслеживание ссылки работает корректно")
    void testTrackLink() {
        // Arrange
        TrackLinkRequest request = new TrackLinkRequest();
        request.setLink("https://example.com");

        // Act
        TrackLinkResponse response = linkTrackingService.trackLink(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusMessage().contains("Successfully started tracking link"));
    }
}
