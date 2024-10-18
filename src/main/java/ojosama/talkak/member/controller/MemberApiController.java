package ojosama.talkak.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ojosama.talkak.common.exception.ErrorResponse;
import ojosama.talkak.member.dto.AdditionalInfoRequest;
import ojosama.talkak.member.dto.AdditionalInfoResponse;
import ojosama.talkak.member.dto.MyPageInfoRequest;
import ojosama.talkak.member.dto.MyPageInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "회원 정보 API", description = "회원 정보 관련 API를 담당합니다.")
public interface MemberApiController {

    @Operation(summary = "추가 정보 입력", description = "추가 정보를 입력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가 정보 입력 성공"),
            @ApiResponse(responseCode = "M001", description = "존재하지 않는 회원입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "M002", description = "회원 정보를 수정하는 데 오류가 발생하였습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<AdditionalInfoResponse> updateAdditionalInfo(AdditionalInfoRequest request,
        Authentication authentication);

    @Operation(summary = "마이페이지 조회", description = "마이페이지의 개인 정보를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공"),
        @ApiResponse(responseCode = "M001", description = "존재하지 않는 회원",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    ResponseEntity<MyPageInfoResponse> getMemberInfo(Authentication authentication);

    @Operation(summary = "마이페이지 수정", description = "마이페이지의 개인 정보를 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "마이페이지 수정 성공"),
        @ApiResponse(responseCode = "M001", description = "존재하지 않는 회원",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "M002", description = "회원 정보 수정 과정에서 오류 발생",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<MyPageInfoResponse> updateMemberInfo(@RequestBody @Valid MyPageInfoRequest myPageInfoRequest, Authentication authentication);

}
