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
<<<<<<< HEAD:src/main/java/ojosama/talkak/video/model/Video.java
import lombok.Getter;
import ojosama.talkak.comment.model.Comment;
=======
import ojosama.talkak.comment.domain.Comment;
>>>>>>> cb827e14a78941820bcceef86b51244432ad0bb0:src/main/java/ojosama/talkak/video/domain/Video.java
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "video")
@Getter
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

    public void incrementLikes() {
        this.countLikes++;
    }

    public void decrementLikes() {
        this.countLikes--;
    }
}
