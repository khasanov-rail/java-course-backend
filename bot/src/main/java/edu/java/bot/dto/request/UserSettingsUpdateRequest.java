package edu.java.bot.dto.request;

public class UserSettingsUpdateRequest {
    private boolean notificationsEnabled;

    public UserSettingsUpdateRequest() {}

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
}
