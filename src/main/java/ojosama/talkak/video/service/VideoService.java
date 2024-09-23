package ojosama.talkak.video.service;

import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.VideoError;
import ojosama.talkak.video.dto.YoutubeUrlValidationAPIResponseDto;
import ojosama.talkak.video.dto.YoutubeUrlValidationRequestDto;
import ojosama.talkak.video.dto.YoutubeUrlValidationResponseDto;
import ojosama.talkak.video.util.WebClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String videoId = Optional.ofNullable(extractVideoId(url)).orElseThrow(() -> new TalKakException(VideoError.VIDEO_NOT_FOUND));


        StringBuilder YOUTUBE_API_URL = new StringBuilder();
        YOUTUBE_API_URL.append(YOUTUBE_API_BASE_URL)
                .append("&id=")
                .append(videoId)
                .append("&key=")
                .append(YOUTUBE_API_KEY);

        YoutubeUrlValidationAPIResponseDto response = webClientUtil.get(YOUTUBE_API_URL.toString(), YoutubeUrlValidationAPIResponseDto.class);

        return new YoutubeUrlValidationResponseDto(response);
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
