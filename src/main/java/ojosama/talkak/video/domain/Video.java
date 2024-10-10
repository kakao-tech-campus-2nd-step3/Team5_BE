package ojosama.talkak.video.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import ojosama.talkak.comment.domain.Comment;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String title;
    private String uniqueFileName;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private Long categoryId;
    private String thumbnail;
    private Boolean isPublic;
    private Long countLikes;
    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Video() {

    }

    public Video(Long id, String title, Long countLikes) {
        this.id = id;
        this.title = title;
        this.countLikes = countLikes;

    }

    public Video(String title, Long memberId, Long categoryId, String uniqueFileName) {
        this.title = title;
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.uniqueFileName = uniqueFileName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUniqueFileName() {
        return uniqueFileName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void incrementLikes() {
        this.countLikes++;
    }

    public void decrementLikes() {
        this.countLikes--;
    }

    public Long getMemberId() {
        return memberId;
    }
}
