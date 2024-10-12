package ojosama.talkak.common.exception.code;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum VideoError implements ErrorCode {

    /* 400 Bad Request */
    INVALID_VIDEO_ID(HttpStatus.BAD_REQUEST, "V001", "유효하지 않은 videoId입니다."),
    YOUTUBE_API_BAD_REQUEST(HttpStatus.BAD_REQUEST, "V002", "유효하지 않은 유튜브 요청입니다.");

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
