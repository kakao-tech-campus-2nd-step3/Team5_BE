package ojosama.talkak.video.controller;

import lombok.RequiredArgsConstructor;
import ojosama.talkak.video.request.AwsS3Request;
import ojosama.talkak.video.response.AwsS3Response;
import ojosama.talkak.video.service.AwsS3Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files/presigned")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("/upload")
    public AwsS3Response getPresignedUrlToUpload(@RequestBody AwsS3Request awsS3Request) {
        return awsS3Service.getPresignedUrlToUpload(awsS3Request);
    }

    @PostMapping("/download")
    public AwsS3Response getPresignedUrlToDownload(@RequestBody AwsS3Request awsS3Request) {
        return awsS3Service.getPresignedUrlToDownload(awsS3Request);
    }

    @PostMapping("/delete")
    public AwsS3Response getPresignedUrlToDelete(@RequestBody AwsS3Request awsS3Request) {
        return awsS3Service.getPresignedUrlToDelete(awsS3Request);
    }
}
