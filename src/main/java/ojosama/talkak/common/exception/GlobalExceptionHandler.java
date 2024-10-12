package ojosama.talkak.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TalKakException.class)
    public ResponseEntity<ErrorResponse> handleTalKakException(TalKakException e) {
        return ResponseEntity.status(e.status())
            .body(ErrorResponse.of(e.status(), e.code(), e.message()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, "000",
                e.getBindingResult().getAllErrors().getFirst().getDefaultMessage()));
    }
}
