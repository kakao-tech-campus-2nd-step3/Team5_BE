package ojosama.talkak.member.controller;

import lombok.RequiredArgsConstructor;
import ojosama.talkak.member.dto.AdditionalInfoRequest;
import ojosama.talkak.member.dto.AdditionalInfoResponse;
import ojosama.talkak.member.dto.MyPageInfoRequest;
import ojosama.talkak.member.dto.MyPageInfoResponse;
import ojosama.talkak.member.service.MyPageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/me")
    public ResponseEntity<MyPageInfoResponse> getMemberInfo() {
        Long memberId = 1L; //추후 로그인 기능 구현 시 수정 예정
        MyPageInfoResponse memberInfo = myPageService.getMemberInfo(memberId);
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    @PutMapping("/me")
    public ResponseEntity<MyPageInfoResponse> updateMemberInfo(@RequestBody MyPageInfoRequest myPageInfoRequest) {
        Long memberId = 1L;
        MyPageInfoResponse memberInfo = myPageService.updateMemberInfo(memberId, myPageInfoRequest);
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    @PatchMapping("/additional-info")
    public ResponseEntity<AdditionalInfoResponse> updateAdditionalInfo(@RequestBody AdditionalInfoRequest request, Authentication authentication) {
//        Long id = Long.valueOf(authentication.getPrincipal().toString());
        Long id = 1L;
        AdditionalInfoResponse response = myPageService.updateAdditionalInfo(id, request);
        return ResponseEntity.ok().body(response);
    }
}
