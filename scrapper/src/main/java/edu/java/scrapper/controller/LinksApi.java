package edu.java.scrapper.controller;

import edu.java.scrapper.dto.api.request.AddLinkRequest;
import edu.java.scrapper.dto.api.request.RemoveLinkRequest;
import edu.java.scrapper.dto.api.response.ApiErrorResponse;
import edu.java.scrapper.dto.api.response.LinkResponse;
import edu.java.scrapper.dto.api.response.ListLinksResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface LinksApi {
    @Operation(
        operationId = "linksDelete",
        summary = "Убрать отслеживание ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/links",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    ResponseEntity<LinkResponse> removeLink(
        @NotNull @Parameter(name = "Tg-Chat-Id", required = true, in = ParameterIn.HEADER)
        @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
        @Parameter(name = "RemoveLinkRequest", required = true) @Valid @RequestBody
        RemoveLinkRequest removeLinkRequest
    );

    @Operation(
        operationId = "linksGet",
        summary = "Получить все отслеживаемые ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/links",
        produces = {"application/json"}
    )
    ResponseEntity<ListLinksResponse> getLinks(
        @NotNull @Parameter(name = "Tg-Chat-Id", required = true, in = ParameterIn.HEADER)
        @RequestHeader(value = "Tg-Chat-Id")
        Long tgChatId
    );

    @Operation(
        operationId = "linksPost",
        summary = "Добавить отслеживание ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/links",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    ResponseEntity<LinkResponse> addLink(
        @NotNull @Parameter(name = "Tg-Chat-Id", required = true, in = ParameterIn.HEADER)
        @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
        @Parameter(name = "AddLinkRequest", required = true) @Valid @RequestBody
        AddLinkRequest addLinkRequest
    );

}
