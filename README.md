# Team5_BE

# week5 코드리뷰 질문

## 도기헌

## 석혜원
.gitignore에 보통 프로퍼티 파일 전체를 추가하는지, 아님 파일 내에 특정 프로필만 추가하는지 궁금합니다!

## 신성민
**지난번 코드 리뷰때 말씀해주셨던 단일 예외 클래스 체계를 팀원분들과 논의하여 아래와 같이 구체화하였는데 적절하게 구현하였는지, 혹시 잘못 설계된 부분은 없는지 궁금합니다!**

common 패키지에 도메인 별로 Error 클래스를 분리한 뒤, 각 클래스 파일 내에서 상황 별로 에러 코드를 커스텀 정의하여 사용하도록 하였습니다. 

ex) Video 관련 예외 상황에 대한 에러 코드

```java
public enum VideoError implements ErrorCode {

    /* 400 Bad Request */
    INVALID_VIDEO_ID(HttpStatus.BAD_REQUEST, "V001", "유효하지 않은 videoId입니다."),
    YOUTUBE_API_BAD_REQUEST(HttpStatus.BAD_REQUEST, "V002", "유효하지 않은 유튜브 요청입니다.");
```

예외를 throw할때는 아래와 같은 방식으로 코드를 작성합니다.

```java
.orElseThrow(() -> TalKakException.of(VideoError.INVALID_VIDEO_ID));
```

기존에 단일 예외 클래스 체계에서의 문제점이 크게 에러 로깅과 테스트 시의 검증의 불명확함이 있었습니다.

(로깅 시 TalkakException 외의 자세한 정보가 출력되지 않음, 테스트 검증 시 TalkakException.class 단위로 예외 상황을 검증하는 것에 대한 모호함)

전자의 경우 스프링 AOP를 적용한 커스텀 예외 로깅을 구현하여 해당되는 에러 코드의 메시지가 출력되도록 하였습니다.

```java
2024-09-27T12:03:23.831+09:00  INFO 72143 --- [talkak] [nio-8080-exec-1] o.talkak.common.aop.LoggingAspect        : Enter: ojosama.talkak.video.controller.VideoController.validateYoutubeUrl() with argument[s] = [YoutubeUrlValidationRequest[url=https://www.youtube.com/watch?v=aaaaaaaaaa]]
-> 2024-09-27T12:03:23.834+09:00  WARN 72143 --- [talkak] [nio-8080-exec-1] o.talkak.common.aop.LoggingAspect        : Exception in ojosama.talkak.video.service.VideoService.validateYoutubeUrl() with cause: 유효하지 않은 videoId입니다.

ojosama.talkak.common.exception.TalKakException: 유효하지 않은 videoId입니다.
```

후자의 경우 TalkakException 내의 에러 코드도 검증하게 하는 별도의 Assertion 클래스를 만들어서 테스트 예외 상황 검증 시에 활용하도록 하였습니다.

```java
public class ExceptionAssertions {
    public static void assertErrorCode(ErrorCode errorCode, ThrowableAssert.ThrowingCallable callable) {
        assertThatThrownBy(callable)
                .isInstanceOf(TalKakException.class)
                .extracting("errorCode")
                .isEqualTo(errorCode);
    }
}
...
assertErrorCode(VideoError.INVALID_VIDEO_ID,
                    () -> videoService.validateYoutubeUrl(new YoutubeUrlValidationRequest(url)));
```
## 탁정민
현재 예상되는 흐름은 다음과 같습니다.
1. AI 서버쪽에서 쇼츠 영상을 반환
2. 반환된 파일을 자바측 서버의 API 호출 = 자바 서버로 multipartFile 타입으로 쇼츠 영상 전송
3. multipartFile 을 S3 에 upload 하고 Video Entity 생성 + VideoResponse DTO 반환

그리고 영상을 다운로드하는 기능을 구현할 때, S3 의 presignedUrl 을 사용하게끔 했습니다.
이때 Video Entity 의 videoUrl 이라는 내부변수에 만약 S3 에 저장된 url 을 그대로 사용하는 경우 보안상의 문제가 있을 것 같아 해당 부분을 uniqueFileName 이라는 내부변수로 변경했습니다. 이는 S3 에 업로드하거나 S3 에서 다운로드할 때 사용되는 키값이 됩니다. 물론 이 경우 2) 변환된 파일을 자바측 서버의 API 호출 단계에서 fileName 을 UUID 혹은 생성일자 등과 같은 방법을 사용하여 unique 한 값이 되도록 해줘야 할 것입니다.

그런데 이렇게 구현하는 경우, 홈화면 등에서 여러개의 Video Entity 를 보여줄 때, S3 에 uniqueFileName 을 사용하여 PresignedUrl 을 일일히 받아야 하는 문제점이 있습니다... 결국 Video Entity 에 S3 에 저장되는 url 을 내부변수로 가지게 해야할까요? 또한 프론트엔드 측에서 유튜브 홈화면처럼 한번에 여러개의 영상을 보여주려고 한다면 결국 원본 영상의 url 노출은 어쩔수 없는건가요?
## 한영진
1. 사용자가 메인 페이지에 들어올 때마다 유튜브 API 호출하는 것을 방지하기 위해 캐시를 활용해서 구현했는데, 현업에서는 자주 사용하는 캐시 시스템에는 어떤 것들이 있는지 궁금합니다.
   그리고 어떤 기준이나 상황에 캐시를 적용하는지 궁금합니다!

3. 현재 사용자가 좋아요를 누른 영상이나 시청 기록을 바탕으로 개인 맞춤형 영상 추천 알고리즘을 구현하고자 합니다. 기존 계획은 Google Video Intelligence API를 사용하여 영상을 분석한 후 추천 알고리즘에 활용하려 했으나, 이 API를 사용하려면 모든 영상을 Google Cloud Storage에 업로드해야 하는 문제가 있었습니다.

    그래서 일단은 Python의 scikit-learn 라이브러리를 활용해 파이썬 서버를 별도로 구축하고 추천 시스템을 구현할 계획입니다. 이렇게 진행해도 문제가 없을지, 또는 기존 Java 기반 서버 내에서 추천 알고리즘을 구현할 수 있는 방법이 있는지 궁금합니다. Java 서버 내에서 추천 시스템을 구현하는 더 나은 접근 방법을 아시는게 있으시면 조언 부탁드립니다!
