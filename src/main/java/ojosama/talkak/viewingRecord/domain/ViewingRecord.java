package ojosama.talkak.viewingRecord.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import ojosama.talkak.member.domain.Member;
import ojosama.talkak.video.domain.Video;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "viewing_records")
public class ViewingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @CreationTimestamp
    private LocalDateTime viewedAt;

    public ViewingRecord() {

    }

    public ViewingRecord(Member member, Video video) {
        this.member = member;
        this.video = video;
    }
}