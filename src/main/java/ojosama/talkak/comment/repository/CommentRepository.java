package ojosama.talkak.comment.repository;

import java.util.List;
import ojosama.talkak.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVideoId(long videoId);
}
