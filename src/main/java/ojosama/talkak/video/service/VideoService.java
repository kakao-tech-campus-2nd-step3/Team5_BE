package ojosama.talkak.video.service;

import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.VideoError;
import ojosama.talkak.video.dto.YoutubeUrlValidationAPIResponse;
import ojosama.talkak.video.dto.YoutubeUrlValidationRequest;
import ojosama.talkak.video.dto.YoutubeUrlValidationResponse;
import ojosama.talkak.video.util.IdExtractor;
import ojosama.talkak.video.util.WebClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoService {

    @Value("${youtube.api-key}")
    private String YOUTUBE_API_KEY;
    @Value("${youtube.api-url}")
    private String YOUTUBE_API_BASE_URL;

    private final WebClientUtil webClientUtil;

    public VideoService(WebClientUtil webClientUtil) {
        this.webClientUtil = webClientUtil;
    }

    public YoutubeUrlValidationResponse validateYoutubeUrl(YoutubeUrlValidationRequest req) {
        String videoId = extractVideoIdOrThrow(req.url());

        String YOUTUBE_API_URL = youtubeApiRequestUrlBuilder(videoId);

        YoutubeUrlValidationAPIResponse response = webClientUtil.get(YOUTUBE_API_URL, YoutubeUrlValidationAPIResponse.class);

        return new YoutubeUrlValidationResponse(response);
    }

    private String youtubeApiRequestUrlBuilder(String videoId) {
        StringBuilder youtubeApiRequestUrl = new StringBuilder();
        youtubeApiRequestUrl.append(YOUTUBE_API_BASE_URL)
                .append("?part=snippet")
                .append("&id=")
                .append(videoId)
                .append("&key=")
                .append(YOUTUBE_API_KEY);
        return youtubeApiRequestUrl.toString();
    }

    private String extractVideoIdOrThrow(String url) {
        String videoId = Optional.ofNullable(IdExtractor.extract(url))
                .orElseThrow(() -> TalKakException.of(VideoError.INVALID_VIDEO_ID));
        return videoId;
    }
}
