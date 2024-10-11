package ojosama.talkak.comment.service;

import java.util.List;
import java.util.Objects;
import ojosama.talkak.comment.dto.CommentRequest;
import ojosama.talkak.comment.dto.CommentResponse;
import ojosama.talkak.comment.domain.Comment;
import ojosama.talkak.comment.repository.CommentRepository;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.CommentError;
import ojosama.talkak.member.domain.Member;
import ojosama.talkak.member.repository.MemberRepository;
import ojosama.talkak.member.dto.MemberResponse;
import ojosama.talkak.video.domain.Video;
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

    public CommentResponse createComment(long memberId, long videoId, CommentRequest commentRequest) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> TalKakException.of(CommentError.INVALID_MEMBER_ID));
        Video video = videoRepository.findById(videoId)
            .orElseThrow(() -> TalKakException.of(CommentError.INVALID_VIDEO_ID));
        Comment comment = new Comment(member, video, commentRequest.content());
        return convertToDTO(commentRepository.save(comment));
    }

    public List<CommentResponse> getCommentsByVideoId(long videoId) {
        return commentRepository.findByVideoId(videoId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    public CommentResponse updateComment(Long commentId, Long memberId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> TalKakException.of(CommentError.INVALID_COMMENT_ID));
        if (!Objects.equals(comment.getMember().getId(), memberId)) {
            throw TalKakException.of(CommentError.UNAUTHORIZED_USER);
        }
        comment.updateContent(commentRequest.content());
        return convertToDTO(commentRepository.save(comment));
    }

    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> TalKakException.of(CommentError.INVALID_COMMENT_ID));
        if (!Objects.equals(comment.getMember().getId(), memberId)) {
            throw TalKakException.of(CommentError.UNAUTHORIZED_USER);
        }
        commentRepository.delete(comment);
    }

    private CommentResponse convertToDTO(Comment comment) {
        Member member = memberRepository.findById(comment.getMember().getId())
            .orElseThrow(() -> TalKakException.of(CommentError.INVALID_MEMBER_ID));
        MemberResponse memberResponse = new MemberResponse(member.getId(),
            member.getImageUrl(), member.getUsername());
        return new CommentResponse(comment.getId(), memberResponse, comment.getContent());
    }

}
