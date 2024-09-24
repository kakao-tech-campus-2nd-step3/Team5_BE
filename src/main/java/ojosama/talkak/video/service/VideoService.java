package ojosama.talkak.video.service;

import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.VideoError;
import ojosama.talkak.video.dto.YoutubeUrlValidationAPIResponseDto;
import ojosama.talkak.video.dto.YoutubeUrlValidationRequestDto;
import ojosama.talkak.video.dto.YoutubeUrlValidationResponseDto;
import ojosama.talkak.video.util.IdExtractor;
import ojosama.talkak.video.util.WebClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoService {

    @Value("${youtube.api-key}")
    private String YOUTUBE_API_KEY;
    private static final String YOUTUBE_API_BASE_URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet";

    private final WebClientUtil webClientUtil;

    public VideoService(WebClientUtil webClientUtil) {
        this.webClientUtil = webClientUtil;
    }

    public YoutubeUrlValidationResponseDto validateYoutubeUrl(YoutubeUrlValidationRequestDto req) {
        String url = req.url();
        String videoId = Optional.ofNullable(IdExtractor.extract(url))
                .orElseThrow(() -> new TalKakException(VideoError.INVALID_VIDEO_ID));


        String YOUTUBE_API_URL = youtubeApiRequestUrlBuilder(videoId);

        YoutubeUrlValidationAPIResponseDto response = webClientUtil.get(YOUTUBE_API_URL, YoutubeUrlValidationAPIResponseDto.class);

        return new YoutubeUrlValidationResponseDto(response);
    }

    private String youtubeApiRequestUrlBuilder(String videoId) {
        StringBuilder youtubeApiRequestUrl = new StringBuilder();
        youtubeApiRequestUrl.append(YOUTUBE_API_BASE_URL)
                .append("&id=")
                .append(videoId)
                .append("&key=")
                .append(YOUTUBE_API_KEY);
        return youtubeApiRequestUrl.toString();
    }
}
