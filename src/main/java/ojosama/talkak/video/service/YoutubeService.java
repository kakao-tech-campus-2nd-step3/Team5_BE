package ojosama.talkak.video.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import ojosama.talkak.category.repository.CategoryRepository;
import ojosama.talkak.video.dto.YoutubeCategoryRequest;
import ojosama.talkak.video.dto.YoutubeResponse;
import ojosama.talkak.video.util.WebClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:application.properties")
public class YoutubeService {
    private final CategoryRepository categoryRepository;
    private final WebClientUtil webClientUtil;
    @Value("${youtube.api-key}")
    private String apiKey;
    public YoutubeService(CategoryRepository categoryRepository, WebClientUtil webClientUtil) {
        this.categoryRepository = categoryRepository;
        this.webClientUtil = webClientUtil;
    }
    private static final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/search";
    // 특정 카테고리가 없는 전체 유튜브 쇼츠 추출
    @Cacheable("youtubeShorts")
    public List<YoutubeResponse> getPopularShorts() throws IOException {
        // 필요한 값들만 불러오도록 최소한의 필드 요청
        String url = YOUTUBE_API_URL + "?part=snippet&fields=items(id(videoId),snippet(publishedAt, title, channelId, thumbnails(default(url))))&q=쇼츠&type=video&key="
            +apiKey+"&maxResults=10";

        String response = webClientUtil.get(url, String.class);
        return parseYoutubeData(response);
    }

    // 특정 카테고리의 유튜브 쇼츠 추출
    public List<YoutubeResponse> getShortsByCategory(YoutubeCategoryRequest youtubeCategoryRequest) throws IOException {
        String categoryName = categoryRepository.findCategoryById(
            youtubeCategoryRequest.categoryId());
        // 필요한 값들만 불러오도록 최소한의 필드 요청
        String url = YOUTUBE_API_URL + "?part=snippet&fields=items(id(videoId),snippet(publishedAt, title, channelId, thumbnails(default(url))))&q=쇼츠, "
            + categoryName + "&type=video&key=" + apiKey + "&maxResults=10";
        String response = webClientUtil.get(url, String.class);
        return parseYoutubeData(response);
    }

    // List 형식으로 변환
    private List<YoutubeResponse> parseYoutubeData(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        List<YoutubeResponse> responses = new ArrayList<>();

        for (JsonNode item : rootNode.path("items")) {
            String videoId = item.path("id").path("videoId").asText();
            String date = item.path("snippet").path("publishedAt").asText();
            String channelId = item.path("snippet").path("channelId").asText();
            String title = item.path("snippet").path("title").asText();
            String thumnailUrl = item.path("snippet").path("thumbnails")
                .path("default").path("url").asText();

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            responses.add(new YoutubeResponse(dateTime, videoId, channelId, title, thumnailUrl));
        }

        return responses;
    }


}