package edu.java.bot.dto.response;

public class UserSettingsUpdateResponse {
    private String message;

    public UserSettingsUpdateResponse() {}

    public UserSettingsUpdateResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
