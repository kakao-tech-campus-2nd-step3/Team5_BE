package ojosama.talkak.video.controller;

import lombok.RequiredArgsConstructor;
import ojosama.talkak.video.dto.AwsS3RequestDto;
import ojosama.talkak.video.dto.AwsS3ResponseDto;
import ojosama.talkak.video.service.AwsS3Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("/presigned/upload")
    public AwsS3ResponseDto getPresignedUrlToUpload(@RequestBody AwsS3RequestDto awsS3RequestDto) {
        return awsS3Service.getPresignedUrlToUpload(awsS3RequestDto);
    }

    @PostMapping("/presigned/download")
    public AwsS3ResponseDto getPresignedUrlToDownload(@RequestBody AwsS3RequestDto awsS3RequestDto) {
        return awsS3Service.getPresignedUrlToDownload(awsS3RequestDto);
    }
}
