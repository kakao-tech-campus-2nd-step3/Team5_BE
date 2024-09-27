package ojosama.talkak.comment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ojosama.talkak.member.model.Member;
import ojosama.talkak.video.model.Video;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;
    private String content;
    public Comment() {
    }

    public Comment(Member member, Video video, String content) {
        this.member = member;
        this.video = video;
        this.content = content;
    }
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }
    public Video getVideo() {
        return video;
    }
    public void updateContent(String newContent) {
        this.content = newContent;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
