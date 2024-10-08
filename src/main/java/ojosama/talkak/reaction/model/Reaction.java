package ojosama.talkak.reaction.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ojosama.talkak.member.model.Member;
import ojosama.talkak.video.model.Video;

@Entity
@Table(name = "reaction")
@Getter
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

    public Reaction(ReactionId reactionId, Member member, Video video, boolean reactionType) {
        this.id = reactionId;
        this.member = member;
        this.video = video;
        this.reaction = reactionType;
    }
  
    public Boolean isReaction() {
        return reaction;
    }

}