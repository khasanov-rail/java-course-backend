package edu.java.scrapper.dto;

public class BotResponse {
    private String status;
    private String message;

    // Геттеры
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // Сеттеры
    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}