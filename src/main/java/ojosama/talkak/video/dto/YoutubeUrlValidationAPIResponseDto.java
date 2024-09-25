package ojosama.talkak.video.dto;

import java.util.List;

public record YoutubeUrlValidationAPIResponseDto(
        List<YoutubeUrlValidationResponseDtoSnippet> items
) {
    public record YoutubeUrlValidationResponseDtoSnippet(
            YoutubeUrlValidationResponseDtoData snippet
    ) {
        public record YoutubeUrlValidationResponseDtoData(
                String title,
                String channelTitle,
                YoutubeUrlValidationResponseDtoThumbnails thumbnails
        ) {
            public record YoutubeUrlValidationResponseDtoThumbnails(
                    YoutubeUrlValidationResponseDtoThumbnailsStandard standard
            ) {
                public record YoutubeUrlValidationResponseDtoThumbnailsStandard(
                        String url
                ) {
                }
            }
        }
    }
}
