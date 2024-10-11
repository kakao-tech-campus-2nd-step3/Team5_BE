package ojosama.talkak.video.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import ojosama.talkak.category.model.CategoryType;
import ojosama.talkak.category.repository.CategoryRepository;
import ojosama.talkak.common.exception.ExceptionAssertions;
import ojosama.talkak.common.exception.code.CategoryError;
import ojosama.talkak.common.util.WebClientUtil;
import ojosama.talkak.video.dto.YoutubeApiResponse;
import ojosama.talkak.video.dto.YoutubeCategoryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
    "youtube.api-key=test-youtube-api-key",
    "youtube.api-url=http://localhost:${wiremock.server.port}"
})
@SpringBootTest
public class YoutubeServiceTest {

    @Value("${youtube.api-key}")
    private String YOUTUBE_API_KEY;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private WebClientUtil webClientUtil;

    @InjectMocks
    private YoutubeService youtubeService;

    @Test
    @DisplayName("유튜브 인기 쇼츠 정보 가져오기 성공 테스트")
    void getPopularShortsSuccessTest() throws IOException {
        String expectedResponse = getMockResponseByPath("payload/get-youtube-popular-shorts-success-response.json");

        when(webClientUtil.get(anyString(), eq(String.class))).thenReturn(expectedResponse);

        stubFor(get(urlPathEqualTo("/youtube/v3/search"))
            .withQueryParam("part", equalTo("snippet"))
            .withQueryParam("q", equalTo("쇼츠"))
            .withQueryParam("key", equalTo(YOUTUBE_API_KEY))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(expectedResponse)
            )
        );

        List<YoutubeApiResponse> response = youtubeService.getPopularShorts();

        assertAll(
            () -> assertThat(response).hasSize(1),
            () -> assertThat(response.get(0).videoId()).isEqualTo("12345"),
            () -> assertThat(response.get(0).title()).isEqualTo("Test Video"),
            () -> assertThat(response.get(0).channelId()).isEqualTo("UC123"),
            () -> assertThat(response.get(0).thumbnailUrl()).isEqualTo("http://example.com/thumbnail.jpg")
        );
    }

    @Test
    @DisplayName("유튜브 카테고리별 쇼츠 정보 가져오기 성공 테스트")
    void getShortsByCategorySuccessTest() throws IOException {
        YoutubeCategoryRequest request = new YoutubeCategoryRequest(1L);
        when(categoryRepository.findCategoryTypeById(1L)).thenReturn(Optional.of(CategoryType.MUSIC));

        String expectedResponse = getMockResponseByPath("payload/get-youtube-category-shorts-success-response.json");
        when(webClientUtil.get(anyString(), eq(String.class))).thenReturn(expectedResponse);

        stubFor(get(urlPathEqualTo("/youtube/v3/search"))
            .withQueryParam("part", equalTo("snippet"))
            .withQueryParam("q", equalTo("쇼츠, 음악"))
            .withQueryParam("key", equalTo(YOUTUBE_API_KEY))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(expectedResponse)
            )
        );

        List<YoutubeApiResponse> response = youtubeService.getShortsByCategory(request);

        assertAll(
            () -> assertThat(response).hasSize(1),
            () -> assertThat(response.get(0).videoId()).isEqualTo("67890"),
            () -> assertThat(response.get(0).title()).isEqualTo("Music Video"),
            () -> assertThat(response.get(0).channelId()).isEqualTo("UC678"),
            () -> assertThat(response.get(0).thumbnailUrl()).isEqualTo("http://example.com/music-thumbnail.jpg")
        );
    }

    @Test
    @DisplayName("잘못된 categoryId로 예외 발생 테스트")
    void getShortsByCategoryInvalidCategoryIdTest() {
        // Given
        YoutubeCategoryRequest request = new YoutubeCategoryRequest(999L); // 존재하지 않는 categoryId 사용
        when(categoryRepository.findCategoryTypeById(999L)).thenReturn(Optional.empty());

        // When & Then
        ExceptionAssertions.assertErrorCode(
            CategoryError.NOT_EXISTING_CATEGORY,
            () -> youtubeService.getShortsByCategory(request)
        );
    }

    private static String getMockResponseByPath(String path) throws IOException {
        ClassLoader classLoader = YoutubeServiceTest.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + path);
            }
            return new String(inputStream.readAllBytes());
        }
    }
}