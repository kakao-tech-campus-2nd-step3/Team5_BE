package ojosama.talkak.video.service;

import ojosama.talkak.video.exception.YoutubeValidationException;
import ojosama.talkak.video.dto.YoutubeUrlValidationRequestDto;
import ojosama.talkak.video.service.VideoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class VideoServiceTest {
    @Autowired
    private VideoService videoService;

    @Test
    @DisplayName("유효한 url 전달 시 videoId가 추출되어야 함")
    void VideoIdExtractionSuccessFromInvalidYoutubeUrl() {
        // given
        String targetId = "ySrznInAJCk";

        // when
        String[] urls = {
                "https://www.youtube.com/watch?v=ySrznInAJCk",
                "http://youtube.com/watch?v=ySrznInAJCk",
                "https://www.youtube.com/embed/ySrznInAJCk",
                "https://youtu.be/ySrznInAJCk",
                "https://youtu.be/ySrznInAJCk?t=9s",
                "https://www.youtube.com/shorts/ySrznInAJCk",
                "https://www.youtube.com/watch?v=ySrznInAJCk",
                "https://youtu.be/ySrznInAJCk",
                "https://www.youtube.com/shorts/ySrznInAJCk"
        };

        // then
        for (String url : urls) {
            assertThat(videoService.extractVideoId(url)).isEqualTo(targetId);
        }
    }

    @Test
    @DisplayName("유효하지 않은 url 전달 시 YoutubeValidationException 예외 발생")
    void VideoIdExtractionFailFromInvalidYoutubeUrl() {
        // given
        // when
        String[] urls = {
                "https://www.youtube.com/watch?v=aaaaaaaaaa",
                "https://www.youtube.com/aaaaaaaaaa",
                "https://www.youtube.com/shorts/aaaaaaaaaa"
        };

        // then
        for (String url : urls) {
            Assertions.assertThrows(YoutubeValidationException.class, () -> videoService.validateYoutubeUrl(new YoutubeUrlValidationRequestDto(url)));
        }
    }
}
