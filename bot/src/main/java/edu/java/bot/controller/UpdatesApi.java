package edu.java.bot.controller;

import edu.java.bot.dto.api.ApiErrorResponse;
import edu.java.bot.dto.api.LinkUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface UpdatesApi {
    @Operation(
        operationId = "updatesPost",
        summary = "Отправить обновление",
        responses = {
            @ApiResponse(responseCode = "200", description = "Обновление обработано"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/updates",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    ResponseEntity<Void> sendUpdate(
        @Parameter(name = "LinkUpdate", required = true) @Valid @RequestBody LinkUpdateRequest linkUpdate
    );
}
