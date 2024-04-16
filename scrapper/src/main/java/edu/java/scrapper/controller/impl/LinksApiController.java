package edu.java.scrapper.controller.impl;

import edu.java.scrapper.controller.LinksApi;
import edu.java.scrapper.dto.api.request.AddLinkRequest;
import edu.java.scrapper.dto.api.request.RemoveLinkRequest;
import edu.java.scrapper.dto.api.response.LinkResponse;
import edu.java.scrapper.dto.api.response.ListLinksResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinksApiController implements LinksApi {
    private final static URI DEFAULT_LINK = URI.create("link");
    private final static Long DEFAULT_ID = 1L;

    @Override
    public ResponseEntity<LinkResponse> removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        LinkResponse linkResponse = new LinkResponse(DEFAULT_ID, removeLinkRequest.link());
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long tgChatId) {
        LinkResponse linkResponse = new LinkResponse(DEFAULT_ID, DEFAULT_LINK);
        List<LinkResponse> linkResponseList = List.of(linkResponse);
        ListLinksResponse listLinksResponse = new ListLinksResponse(linkResponseList, 1);
        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LinkResponse> addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        LinkResponse linkResponse = new LinkResponse(DEFAULT_ID, addLinkRequest.link());
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }
}
