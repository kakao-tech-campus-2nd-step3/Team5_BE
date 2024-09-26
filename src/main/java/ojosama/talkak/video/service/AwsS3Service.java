package ojosama.talkak.video.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import ojosama.talkak.video.dto.AwsS3Request;
import ojosama.talkak.video.dto.AwsS3Response;
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

    public AwsS3Response getPresignedUrlToUpload(AwsS3Request request) {
        GeneratePresignedUrlRequest presignedUrlRequest = generatePresignedUrlRequest(request, HttpMethod.PUT);
        return new AwsS3Response(amazonS3.generatePresignedUrl(presignedUrlRequest).toString());
    }

    public AwsS3Response getPresignedUrlToDownload(AwsS3Request request) {
        GeneratePresignedUrlRequest presignedUrlRequest = generatePresignedUrlRequest(request, HttpMethod.GET);
        return new AwsS3Response(amazonS3.generatePresignedUrl(presignedUrlRequest).toString());
    }

    public AwsS3Response getPresignedUrlToDelete(AwsS3Request request) {
        GeneratePresignedUrlRequest presignedUrlRequest = generatePresignedUrlRequest(request, HttpMethod.DELETE);
        return new AwsS3Response(amazonS3.generatePresignedUrl(presignedUrlRequest).toString());
    }

    public GeneratePresignedUrlRequest generatePresignedUrlRequest(AwsS3Request request, HttpMethod httpMethod) {
        String filename = request.filename();
        Date expirationTime = getExpirationTime();
        GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(bucket, filename)
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
