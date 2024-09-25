package ojosama.talkak.video.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import ojosama.talkak.video.dto.AwsS3RequestDto;
import ojosama.talkak.video.dto.AwsS3ResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public AwsS3ResponseDto getPresignedUrlToUpload(AwsS3RequestDto request) {
        String filename = request.filename();
        Date expirationTime = getExpirationTime();
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, filename)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expirationTime);
        return new AwsS3ResponseDto(amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString());
    }

    public AwsS3ResponseDto getPresignedUrlToDownload(AwsS3RequestDto request) {
        String filename = request.filename();
        Date expirationTime = getExpirationTime();
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, filename)
                .withMethod(HttpMethod.GET)
                .withExpiration(expirationTime);
        return new AwsS3ResponseDto(amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString());
    }

    public static Date getExpirationTime() {
        Date expiration = new Date();
        long expTime = expiration.getTime();
        expTime += TimeUnit.MINUTES.toMillis(3);
        expiration.setTime(expTime);
        return expiration;
    }
}
