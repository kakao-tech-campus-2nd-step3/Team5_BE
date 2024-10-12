package ojosama.talkak.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TalKakException.class)
    public ResponseEntity<ErrorResponse> handleTalKakException(TalKakException e) {
        return ResponseEntity.status(e.status())
            .body(ErrorResponse.of(e.status(), e.code(), e.message()));
    }
}
