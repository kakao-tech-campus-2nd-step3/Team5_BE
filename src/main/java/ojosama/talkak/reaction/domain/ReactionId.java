package ojosama.talkak.reaction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReactionId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "video_id")
    private Long videoId;

    // 기본 생성자
    public ReactionId() {
    }

    public ReactionId(Long memberId, Long videoId) {
        this.memberId = memberId;
        this.videoId = videoId;
    }

    // Getters and setters
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReactionId that = (ReactionId) o;
        return Objects.equals(memberId, that.memberId) && Objects.equals(videoId, that.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, videoId);
    }
}