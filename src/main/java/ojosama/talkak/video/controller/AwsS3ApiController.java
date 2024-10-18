package ojosama.talkak.video.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ojosama.talkak.common.exception.ErrorResponse;
import ojosama.talkak.video.request.AwsS3Request;
import ojosama.talkak.video.response.AwsS3Response;
import org.springframework.web.bind.annotation.RequestBody;

public interface AwsS3ApiController {

    @Operation(summary = "업로드용 presigned url 받아오기", description = "업로드용 presigned url을 받아옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "presigend url 받기 성공"),
            @ApiResponse(responseCode = "W001", description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "W002", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    AwsS3Response getPresignedUrlToUpload(@RequestBody AwsS3Request awsS3Request);

    @Operation(summary = "다운로드용 presigned url 받아오기", description = "다운로드용 presigned url을 받아옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "presigend url 받기 성공"),
            @ApiResponse(responseCode = "W001", description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "W002", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    AwsS3Response getPresignedUrlToDownload(@RequestBody AwsS3Request awsS3Request);

    @Operation(summary = "삭제용 presigned url 받아오기", description = "삭제용 presigned url을 받아옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "presigend url 받기 성공"),
            @ApiResponse(responseCode = "W001", description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "W002", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    AwsS3Response getPresignedUrlToDelete(@RequestBody AwsS3Request awsS3Request);
}
