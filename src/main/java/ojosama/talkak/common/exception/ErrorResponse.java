package ojosama.talkak.common.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
    String code,
    String message,
    String data
) {
    public static ErrorResponse of(HttpStatus status, String code, String message) {
        return new ErrorResponse(code, message, status.value() + " " + status.getReasonPhrase());
    }
}
