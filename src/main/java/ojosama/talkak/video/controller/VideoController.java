package ojosama.talkak.video.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import ojosama.talkak.video.dto.VideoRequest;
import ojosama.talkak.video.dto.VideoResponse;
import ojosama.talkak.video.dto.YoutubeApiResponse;
import ojosama.talkak.video.dto.YoutubeCategoryRequest;
import ojosama.talkak.video.dto.YoutubeUrlValidationRequest;
import ojosama.talkak.video.dto.YoutubeUrlValidationResponse;
import ojosama.talkak.video.service.AwsS3Service;
import ojosama.talkak.video.service.VideoService;
import ojosama.talkak.video.service.YoutubeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;
    private final YoutubeService youtubeService;
    private final AwsS3Service awsS3Service;

    public VideoController(VideoService videoService, YoutubeService youtubeService,
        AwsS3Service awsS3Service) {
        this.videoService = videoService;
        this.youtubeService = youtubeService;
        this.awsS3Service = awsS3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<VideoResponse> uploadShortsVideo(
        @RequestParam("file") MultipartFile file, VideoRequest videoRequest) throws IOException {
        VideoResponse response = awsS3Service.uploadVideo(file, videoRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{videoId}/extract")
    public ResponseEntity<URL> downloadVideo(@PathVariable Long videoId)
        throws MalformedURLException {
        URL downloadUrl = awsS3Service.generateDownloadUrl(videoId);
        return ResponseEntity.ok(downloadUrl);
    }

    @PostMapping("/youtube-url-validation")
    public ResponseEntity<YoutubeUrlValidationResponse> validateYoutubeUrl(
        @RequestBody YoutubeUrlValidationRequest req) {
        YoutubeUrlValidationResponse response = videoService.validateYoutubeUrl(req);
        return ResponseEntity.ok(response);
    }

    // 메인페이지에서 유튜브 관련 영상 불러오기(카테고리 지정 X)
    @GetMapping("/youtube")
    public ResponseEntity<List<YoutubeApiResponse>> getPopularYoutubeShorts() throws IOException {
        List<YoutubeApiResponse> response = youtubeService.getPopularShorts();
        return ResponseEntity.ok(response);
    }

    // 메인페이지에서 유튜브 관련 영상 불러오기(카테고리 지정)
    @GetMapping("/youtube/{categoryId}")
    public ResponseEntity<List<YoutubeApiResponse>> getPopularYoutubeShortsByCategory(
        @PathVariable("categoryId")
        YoutubeCategoryRequest youtubeCategoryRequest) throws IOException {
        List<YoutubeApiResponse> response = youtubeService.getShortsByCategory(
            youtubeCategoryRequest);
        return ResponseEntity.ok(response);
    }
}
