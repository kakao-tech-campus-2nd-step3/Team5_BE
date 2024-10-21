package ojosama.talkak.common.exception.code;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum WebClientError implements ErrorCode {
    /* 400 Bad Request */
    WEB_BAD_REQUEST(HttpStatus.BAD_REQUEST, "W001", "유효하지 않은 요청입니다."),
    WEB_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "W002", "서버 내부에서 오류가 발생했습니다.");

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
