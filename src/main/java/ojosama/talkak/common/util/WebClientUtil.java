package ojosama.talkak.common.util;

import lombok.RequiredArgsConstructor;
import ojosama.talkak.common.config.WebClientConfig;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> T get(String url, Class<T> responseDtoClass) {
        return webClientConfig.webClient().method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .bodyToMono(responseDtoClass)
                .block();
    }
}
