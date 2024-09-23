package ojosama.talkak.video.dto;

public record YoutubeUrlValidationResponseDto(String title, String user, String url) {
    public YoutubeUrlValidationResponseDto(
            YoutubeUrlValidationAPIResponseDto response
    ) {
        this(
                response.items().get(0).snippet().title(),
                response.items().get(0).snippet().channelTitle(),
                response.items().get(0).snippet().thumbnails().standard().url()
        );
    }
}







