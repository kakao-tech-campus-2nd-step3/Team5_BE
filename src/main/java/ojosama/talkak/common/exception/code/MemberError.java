package ojosama.talkak.common.exception.code;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberError implements ErrorCode {

    NOT_EXISTING_MEMBER(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 회원입니다."),
    ERROR_UPDATE_MEMBER_INFO(HttpStatus.BAD_REQUEST, "M002", "회원 정보를 수정하는데 오류가 발생하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus status() {
        return status;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
