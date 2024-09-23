package ojosama.talkak.video.controller;

import java.io.IOException;
import java.util.List;
import ojosama.talkak.video.dto.YoutubeResponse;
import ojosama.talkak.video.dto.YoutubeUrlValidationRequestDto;
import ojosama.talkak.video.dto.YoutubeUrlValidationResponseDto;
import ojosama.talkak.video.service.VideoService;
import ojosama.talkak.video.service.YoutubeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;
    private final YoutubeService youtubeService;

    public VideoController(VideoService videoService, YoutubeService youtubeService) {
        this.videoService = videoService;
        this.youtubeService = youtubeService;
    }

    @PostMapping("/youtube-valid")
    public ResponseEntity<YoutubeUrlValidationResponseDto> validateYoutubeUrl(@RequestBody YoutubeUrlValidationRequestDto req) {
        YoutubeUrlValidationResponseDto response = videoService.validateYoutubeUrl(req);
        return ResponseEntity.ok(response);
    }

    // 메인페이지에서 유튜브 관련 영상 불러오기(카테고리 지정 X)
    @GetMapping("/youtube")
    public ResponseEntity<List<YoutubeResponse>> getPopularYoutubeShorts() throws IOException {
        List<YoutubeResponse> response = youtubeService.getPopularShorts();
        return ResponseEntity.ok(response);
    }

    // 메인페이지에서 유튜브 관련 영상 불러오기(카테고리 지정)
    @GetMapping("/youtube/{category}")
    public ResponseEntity<List<YoutubeResponse>> getPopularYoutubeShortsByCategory(@PathVariable("category") String category) throws IOException {
        List<YoutubeResponse> response =  youtubeService.getShortsByCategory(category);
        return ResponseEntity.ok(response);
    }

}
