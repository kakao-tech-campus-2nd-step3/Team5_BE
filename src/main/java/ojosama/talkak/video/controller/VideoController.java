package ojosama.talkak.video.controller;

import ojosama.talkak.video.dto.YoutubeUrlValidationRequestDto;
import ojosama.talkak.video.dto.YoutubeUrlValidationResponseDto;
import ojosama.talkak.video.exception.YoutubeValidationException;
import ojosama.talkak.video.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/youtube-valid")
    public ResponseEntity<YoutubeUrlValidationResponseDto> validateYoutubeUrl(@RequestBody YoutubeUrlValidationRequestDto req) {
        YoutubeUrlValidationResponseDto response = videoService.validateYoutubeUrl(req);
        return ResponseEntity.ok(response);
    }
}
