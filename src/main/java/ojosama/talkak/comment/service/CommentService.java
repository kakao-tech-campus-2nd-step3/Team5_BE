package ojosama.talkak.comment.service;

import java.util.List;
import ojosama.talkak.comment.dto.CommentRequest;
import ojosama.talkak.comment.dto.CommentResponse;
import ojosama.talkak.comment.model.Comment;
import ojosama.talkak.comment.repository.CommentRepository;
import ojosama.talkak.member.model.Member;
import ojosama.talkak.member.repository.MemberRepository;
import ojosama.talkak.member.dto.MemberResponse;
import ojosama.talkak.video.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;

    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository,
        VideoRepository videoRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.videoRepository = videoRepository;
    }

    public CommentResponse addComment(long memberId, long videoId, CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setMember(memberRepository.findById(memberId).orElseThrow());
        comment.setVideo(videoRepository.findById(videoId).orElseThrow());
        comment.setContent(commentRequest.content());
        return convertToDTO(commentRepository.save(comment));
    }

    public List<CommentResponse> getCommentsByVideoId(long videoId) {
        return commentRepository.findByVideoId(videoId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    private CommentResponse convertToDTO(Comment comment) {
        Member member = comment.getMember();
        MemberResponse memberResponse = new MemberResponse(member.getId(),
            member.getImageUrl(), member.getUsername());
        return new CommentResponse(comment.getId(), memberResponse, comment.getContent());
    }

}
