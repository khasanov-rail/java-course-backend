package edu.java.bot.dto.response;

public class TrackLinkResponse {
    private String statusMessage;

    public TrackLinkResponse(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
