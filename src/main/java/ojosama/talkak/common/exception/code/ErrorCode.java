package ojosama.talkak.common.exception.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus status();
    String code();
    String message();
}
