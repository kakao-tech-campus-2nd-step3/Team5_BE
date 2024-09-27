package ojosama.talkak.video.dto;
import java.time.LocalDateTime;

public record YoutubeApiResponse(LocalDateTime date, String videoId, String channelId, String title, String thumbnailUrl) {

}
