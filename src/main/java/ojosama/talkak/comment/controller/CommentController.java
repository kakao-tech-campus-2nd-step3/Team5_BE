package ojosama.talkak.comment.controller;

import java.util.List;
import ojosama.talkak.comment.dto.CommentRequest;
import ojosama.talkak.comment.dto.CommentResponse;
import ojosama.talkak.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/videos/{videoId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
        @PathVariable("video_id") Long videoId,
        @RequestHeader("memberId") Long memberId,
        @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.createComment(videoId, memberId, commentRequest));
    }

    @GetMapping
    public ResponseEntity<?> getCommentsByVideoId(@PathVariable Long videoId) {
        List<CommentResponse> comments = commentService.getCommentsByVideoId(videoId);
        return ResponseEntity.ok().body(comments);
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<String> updateComment(
        @PathVariable("comment_id") Long commentId,
        @RequestHeader("memberId") Long memberId,
        @RequestBody CommentRequest commentRequest) {
        commentService.updateComment(commentId, memberId, commentRequest);
        return ResponseEntity.ok("Comment updated successfully");
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> deleteComment(
        @PathVariable("comment_id") Long commentId,
        @RequestHeader("memberId") Long memberId) {
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
