package ojosama.talkak.request;

public class YoutubeUrlValidationRequestDto {
    private String url;

    public YoutubeUrlValidationRequestDto(String url) {
        this.url = url;
    }

    public YoutubeUrlValidationRequestDto() {
    }

    public String getUrl() {
        return url;
    }
}
