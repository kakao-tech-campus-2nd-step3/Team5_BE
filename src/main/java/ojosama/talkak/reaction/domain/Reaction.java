package ojosama.talkak.reaction.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ojosama.talkak.member.domain.Member;
import ojosama.talkak.video.domain.Video;

@Entity
@Table(name = "reaction")
public class Reaction {

    @EmbeddedId
    private ReactionId id;  // 복합 키를 포함하는 필드

    private Boolean reaction;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "video_id", insertable = false, updatable = false)
    private Video video;

    public Reaction() {
    }

    public Reaction(ReactionId reactionId, Member member, Video video, boolean reactionType) {
        this.id = reactionId;
        this.member = member;
        this.video = video;
        this.reaction = reactionType;
    }

    // Getters and setters
    public ReactionId getId() {
        return id;
    }

    public Boolean isReaction() {
        return reaction;
    }

    public Member getMember() {
        return member;
    }

    public Video getVideo() {
        return video;
    }
}