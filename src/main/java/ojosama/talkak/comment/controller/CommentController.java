package ojosama.talkak.comment.controller;

import java.util.List;
import ojosama.talkak.comment.dto.CommentResponse;
import ojosama.talkak.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/videos/{videoId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<?> getCommentsByVideoId(@PathVariable Long videoId) {
        List<CommentResponse> comments = commentService.getCommentsByVideoId(videoId);
        return ResponseEntity.ok().body(comments);
    }

}
