package edu.java.bot.dto.response;

public class ChatResponse {
    private String message;

    public ChatResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
