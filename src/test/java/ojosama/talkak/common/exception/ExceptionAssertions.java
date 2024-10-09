package ojosama.talkak.common.exception;

import ojosama.talkak.common.exception.code.ErrorCode;
import org.assertj.core.api.ThrowableAssert;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExceptionAssertions {
    public static void assertErrorCode(ErrorCode errorCode, ThrowableAssert.ThrowingCallable callable) {
        assertThatThrownBy(callable)
                .isInstanceOf(TalKakException.class)
                .extracting("errorCode")
                .isEqualTo(errorCode);
    }
}