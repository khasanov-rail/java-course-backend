package edu.java.bot.dto.request;

public class NotificationSettingRequest {
    private boolean enableNotifications;

    public boolean isEnableNotifications() {
        return enableNotifications;
    }

    public void setEnableNotifications(boolean enableNotifications) {
        this.enableNotifications = enableNotifications;
    }
}
