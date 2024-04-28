package edu.java.scrapper.controller.impl;

import edu.java.scrapper.controller.LinksApi;
import edu.java.scrapper.dto.api.request.AddLinkRequest;
import edu.java.scrapper.dto.api.request.RemoveLinkRequest;
import edu.java.scrapper.dto.api.response.LinkResponse;
import edu.java.scrapper.dto.api.response.ListLinksResponse;
import edu.java.scrapper.mapper.LinkMapper;
import edu.java.scrapper.service.LinkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequiredArgsConstructor public class LinksApiController implements LinksApi {
    private final LinkService linkService;
    private final LinkMapper linkMapper;

    @Override public ResponseEntity<LinkResponse> removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return new ResponseEntity<>(
            linkMapper.toDto(linkService.remove(tgChatId, removeLinkRequest.link())),
            HttpStatus.OK
        );
    }

    @Override public ResponseEntity<ListLinksResponse> getLinks(Long tgChatId) {
        List<LinkResponse> linkResponses = linkMapper.toDtoList(linkService.listAll(tgChatId));
        return new ResponseEntity<>(new ListLinksResponse(linkResponses, linkResponses.size()), HttpStatus.OK);
    }

    @Override public ResponseEntity<LinkResponse> addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return new ResponseEntity<>(
            linkMapper.toDto(linkService.add(tgChatId, addLinkRequest.link())),
            HttpStatus.OK
        );
    }
}
