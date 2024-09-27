package ojosama.talkak.reaction.controller;

import ojosama.talkak.reaction.service.ReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/videos/{videoId}/reactions")
public class ReactionController {

    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PostMapping("/like")
    public ResponseEntity<Void> toggleLike(
        @PathVariable Long videoId,
        @RequestHeader("memberId") Long memberId) {
        reactionService.toggleLike(videoId, memberId);
        return ResponseEntity.ok().build();
    }
}
