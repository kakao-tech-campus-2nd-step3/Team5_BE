package ojosama.talkak.common.exception.code;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum VideoError implements ErrorCode {

    /* 400 Bad Request */
    VIDEO_NOT_FOUND(HttpStatus.BAD_REQUEST, "V001", "존재하지 않는 영상입니다.");

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
