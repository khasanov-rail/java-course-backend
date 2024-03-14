package edu.java.scrapper.dto;

public class NotificationRequest {
    private String chatId;
    private String message;

    public NotificationRequest() {
    }

    public NotificationRequest(String chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    // Геттеры
    public String getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }

    // Сеттеры
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
