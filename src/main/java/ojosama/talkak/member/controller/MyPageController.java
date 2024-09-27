package ojosama.talkak.member.controller;

import lombok.RequiredArgsConstructor;
import ojosama.talkak.member.dto.MyPageInfoRequest;
import ojosama.talkak.member.dto.MyPageInfoResponse;
import ojosama.talkak.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/api/me")
    public ResponseEntity<MyPageInfoResponse> getMemberInfo() {
        Long memberId = 1L; //추후 로그인 기능 구현 시 수정 예정
        MyPageInfoResponse memberInfo = memberService.getMemberInfo(memberId);
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    @PutMapping("/api/me")
    public ResponseEntity<MyPageInfoResponse> updateMemberInfo(@RequestBody MyPageInfoRequest myPageInfoRequest) {
        Long memberId = 1L;
        MyPageInfoResponse memberInfo = memberService.updateMemberInfo(memberId, myPageInfoRequest);
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

}
