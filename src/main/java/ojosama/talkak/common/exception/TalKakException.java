package ojosama.talkak.common.exception;

import ojosama.talkak.common.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class TalKakException extends RuntimeException{

    private final ErrorCode errorCode;

    private TalKakException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public static TalKakException of(ErrorCode errorCode) {
        return new TalKakException(errorCode);
    }

    public HttpStatus status() {
        return errorCode.status();
    }

    public String code() {
        return errorCode.code();
    }

    public String message() {
        return errorCode.message();
    }
}
