package ojosama.talkak.video.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.VideoError;
import ojosama.talkak.video.request.AwsS3Request;
import ojosama.talkak.video.response.AwsS3Response;
import ojosama.talkak.video.request.VideoRequest;
import ojosama.talkak.video.response.VideoResponse;
import ojosama.talkak.video.domain.Video;
import ojosama.talkak.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;
    private final VideoRepository videoRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public VideoResponse uploadVideo(MultipartFile file, VideoRequest videoRequest)
        throws IOException {
        String fileName = file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null));
        Video video = new Video(videoRequest.title(), videoRequest.memberId(),
            videoRequest.categoryId(), fileName);
        video = videoRepository.save(video);
        return new VideoResponse(video.getId(), video.getTitle(), video.getMemberId(),
            video.getCategoryId(), video.getUniqueFileName());
    }

    public URL generateDownloadUrl(Long id) throws MalformedURLException {
        Video video = videoRepository.findById(id)
            .orElseThrow(() -> TalKakException.of(VideoError.INVALID_VIDEO_ID));
        AwsS3Request request = new AwsS3Request(video.getUniqueFileName());
        AwsS3Response response = getPresignedUrlToDownload(request);
        return new URL(response.url());
    }

    public AwsS3Response getPresignedUrlToUpload(AwsS3Request request) {
        GeneratePresignedUrlRequest presignedUrlRequest = generatePresignedUrlRequest(request,
            HttpMethod.PUT);
        return new AwsS3Response(amazonS3.generatePresignedUrl(presignedUrlRequest).toString());
    }

    public AwsS3Response getPresignedUrlToDownload(AwsS3Request request) {
        GeneratePresignedUrlRequest presignedUrlRequest = generatePresignedUrlRequest(request,
            HttpMethod.GET);
        return new AwsS3Response(amazonS3.generatePresignedUrl(presignedUrlRequest).toString());
    }

    public AwsS3Response getPresignedUrlToDelete(AwsS3Request request) {
        GeneratePresignedUrlRequest presignedUrlRequest = generatePresignedUrlRequest(request,
            HttpMethod.DELETE);
        return new AwsS3Response(amazonS3.generatePresignedUrl(presignedUrlRequest).toString());
    }

    public GeneratePresignedUrlRequest generatePresignedUrlRequest(AwsS3Request request,
        HttpMethod httpMethod) {
        String filename = request.filename();
        Date expirationTime = getExpirationTime();
        GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(bucket,
            filename)
            .withMethod(httpMethod)
            .withExpiration(expirationTime);
        return presignedUrlRequest;
    }

    public static Date getExpirationTime() {
        Date expiration = new Date();
        long expTime = expiration.getTime();
        expTime += TimeUnit.MINUTES.toMillis(3);
        expiration.setTime(expTime);
        return expiration;
    }
}
