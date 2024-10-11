package ojosama.talkak.video.service;

import ojosama.talkak.video.request.YoutubeUrlValidationRequest;
import ojosama.talkak.video.response.YoutubeUrlValidationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "youtube.api-key=test-youtube-api-key",
        "youtube.api-url=http://localhost:${wiremock.server.port}"
})
@SpringBootTest
public class YouTubeApiIntegrationTest {

    @Value("${youtube.api-key}")
    private String YOUTUBE_API_KEY;

    @Autowired
    private VideoService videoService;

    @Test
    @DisplayName("유튜브 url로 정보 가져오기 성공 테스트")
    void getYoutubeVideoInfoSuccessTest() throws IOException {
        String videoId = "ySrznInAJCk";
        String expectedResponse = getMockResponseByPath("payload/get-youtube-api-success-response.json");

        stubFor(get(urlEqualTo(String.format("/?part=snippet&id=%s&key=%s", videoId, YOUTUBE_API_KEY)))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedResponse)
                )
        );

        YoutubeUrlValidationResponse response = videoService.validateYoutubeUrl(new YoutubeUrlValidationRequest(String.format("https://www.youtube.com/watch?v=%s", videoId)));

        assertAll(
                () -> assertThat(response.title()).isEqualTo("testTitle"),
                () -> assertThat(response.user()).isEqualTo("testUser"),
                () -> assertThat(response.url()).isEqualTo("testThumbnailsStandardUrl")
        );
    }

    @Test
    @DisplayName("유튜브 url로 정보 가져오기 실패 테스트 - 특정 필드(url)가 존재하지 않는 경우")
    void getYoutubeVideoInfoFailTest() throws IOException {
        String videoId = "ySrznInAJCk";
        String expectedResponse = getMockResponseByPath("payload/get-youtube-api-fail-response.json");

        stubFor(get(urlEqualTo(String.format("/?part=snippet&id=%s&key=%s", videoId, YOUTUBE_API_KEY)))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedResponse)
                )
        );

        YoutubeUrlValidationResponse response = videoService.validateYoutubeUrl(new YoutubeUrlValidationRequest(String.format("https://www.youtube.com/watch?v=%s", videoId)));

        assertAll(
                () -> assertThat(response.title()).isEqualTo("testTitle"),
                () -> assertThat(response.user()).isEqualTo("testUser"),
                () -> assertThat(response.url()).isNull()
        );
    }

    private static String getMockResponseByPath(String path) throws IOException {
        ClassLoader classLoader = YouTubeApiIntegrationTest.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + path);
            }
            return new String(inputStream.readAllBytes());
        }
    }
}
