package edu.java.bot.dto.request;

public class StackOverflowInfoRequest {
    private String tag;

    public StackOverflowInfoRequest() {
    }

    public StackOverflowInfoRequest(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "StackOverflowInfoRequest{"
            + "tag='" + tag + '\''
            + '}';
    }
}
