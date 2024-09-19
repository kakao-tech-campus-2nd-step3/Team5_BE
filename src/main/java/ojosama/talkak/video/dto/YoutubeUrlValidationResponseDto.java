package ojosama.talkak.video.dto;

public class YoutubeUrlValidationResponseDto {
    private String title;
    private String user;
    private String thumbnail;

    public YoutubeUrlValidationResponseDto(String title, String user, String thumbnail) {
        this.title = title;
        this.user = user;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
