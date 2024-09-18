package ojosama.talkak.controller;

import ojosama.talkak.request.YoutubeUrlValidationRequestDto;
import ojosama.talkak.response.YoutubeUrlValidationResponseDto;
import ojosama.talkak.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
