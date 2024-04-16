package edu.java.bot.link;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Link {
    private LinkInfo linkInfo;
    private URI uri;
    private String resource;
}
