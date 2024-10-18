package ojosama.talkak.video.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ojosama.talkak.common.exception.ErrorResponse;
import ojosama.talkak.video.request.YoutubeUrlValidationRequest;
import ojosama.talkak.video.response.YoutubeUrlValidationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface VideoApiController {
    @Operation(summary = "유튜브 url 검증", description = "유튜브 url을 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유튜브 url 조회 성공"),
            @ApiResponse(responseCode = "V001", description = "존재하지 않는 유튜브 url",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    ResponseEntity<YoutubeUrlValidationResponse> validateYoutubeUrl(@RequestBody YoutubeUrlValidationRequest req);
}
