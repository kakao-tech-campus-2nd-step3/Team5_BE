package ojosama.talkak.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ojosama.talkak.exception.YoutubeValidationException;
import ojosama.talkak.request.YoutubeUrlValidationRequestDto;
import ojosama.talkak.response.YoutubeUrlValidationResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VideoService {

    @Value("${youtube.api-key}")
    private String YOUTUBE_API_KEY;

    public YoutubeUrlValidationResponseDto validateYoutubeUrl(YoutubeUrlValidationRequestDto req) {
        String url = req.getUrl();
        String videoId = Optional.ofNullable(extractVideoId(url)).orElseThrow(() -> new YoutubeValidationException("Invalid Youtube URL"));

        String YOUTUBE_API_BASE_URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet";

        StringBuilder YOUTUBE_API_URL = new StringBuilder();
        YOUTUBE_API_URL.append(YOUTUBE_API_BASE_URL)
                .append("&id=")
                .append(videoId)
                .append("&key=")
                .append(YOUTUBE_API_KEY);

        WebClient webClient = WebClient.builder().build();
        String responseString = webClient.get()
                .uri(YOUTUBE_API_URL.toString())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseString);
            JsonNode itemNode = rootNode.path("items").get(0);
            JsonNode snippetNode = itemNode.path("snippet");

            String title = snippetNode.path("title").asText();
            String user = snippetNode.path("channelTitle").asText();
            String thumbnail = snippetNode.path("thumbnails").path("standard").path("url").asText();

            return new YoutubeUrlValidationResponseDto(title, user, thumbnail);
        } catch (Exception e) {
            throw new YoutubeValidationException("Invalid YouTube API response");
        }
    }

    public String extractVideoId(String url) {
        Pattern pattern = Pattern.compile("(?:v=|\\/)([a-zA-Z0-9_-]{11})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
