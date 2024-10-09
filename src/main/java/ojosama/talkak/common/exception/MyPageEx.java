package ojosama.talkak.common.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ojosama.talkak.common.exception.code.MemberError;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyPageEx {

    Class<? extends RuntimeException> exceptionType();

    MemberError errorCode();
}
