package edu.java.bot.dto.response;

public class NotificationSettingResponse {
    private String message;

    public NotificationSettingResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
