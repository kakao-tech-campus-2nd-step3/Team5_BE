package ojosama.talkak.video.dto;

public record YoutubeUrlValidationResponse(String title, String user, String url) {
    public YoutubeUrlValidationResponse(
            YoutubeUrlValidationAPIResponse response
    ) {
        this(
                response.items().get(0).snippet().title(),
                response.items().get(0).snippet().channelTitle(),
                response.items().get(0).snippet().thumbnails().standard().url()
        );
    }
}







