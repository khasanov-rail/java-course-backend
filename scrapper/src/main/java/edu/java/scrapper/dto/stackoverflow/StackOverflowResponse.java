package edu.java.scrapper.dto.stackoverflow;

import java.util.List;

public class StackOverflowResponse {
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
