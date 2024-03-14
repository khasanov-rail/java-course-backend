package edu.java.bot.dto.request;

public class UserRegistrationRequest {
    private String username;
    private String email;

    public UserRegistrationRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
