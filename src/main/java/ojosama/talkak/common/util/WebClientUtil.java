package ojosama.talkak.common.util;

import lombok.RequiredArgsConstructor;
import ojosama.talkak.common.config.WebClientConfig;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.WebClientError;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> T get(String url, Class<T> responseDtoClass) {
        return webClientConfig.webClient().method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse ->
                        Mono.error(TalKakException.of(WebClientError.WEB_BAD_REQUEST))
                )
                .onStatus(status -> status.is5xxServerError(), clientResponse ->
                        Mono.error(TalKakException.of(WebClientError.WEB_INTERNAL_SERVER_ERROR))
                )
                .bodyToMono(responseDtoClass)
                .block();
    }
}
