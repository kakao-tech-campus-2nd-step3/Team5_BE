package ojosama.talkak.comment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ojosama.talkak.comment.dto.CommentRequest;
import ojosama.talkak.comment.dto.CommentResponse;
import ojosama.talkak.comment.model.Comment;
import ojosama.talkak.comment.repository.CommentRepository;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.member.model.Member;
import ojosama.talkak.member.repository.MemberRepository;
import ojosama.talkak.video.model.Video;
import ojosama.talkak.video.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private CommentRepository commentRepository;

    private Member member;
    private Video video;
    private Comment comment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        member = new Member(1L, "username");
        video = new Video(1L, "video title", 0L);
        comment = new Comment(1L, member, video, "This is a comment.");
    }

    @Test
    public void testCreateComment() {
        // Given
        Long memberId = 1L;
        Long videoId = 1L;
        CommentRequest commentRequest = new CommentRequest("This is a comment.");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // When
        CommentResponse response = commentService.createComment(memberId, videoId, commentRequest);

        // Then
        assertEquals(commentRequest.content(), response.content());
        assertEquals(member.getId(), response.member().memberId());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    public void testCreateComment_InvalidMember() {
        // Given
        Long memberId = 1L;
        Long videoId = 1L;
        CommentRequest commentRequest = new CommentRequest("This is a comment.");

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));

        // When & Then
        TalKakException exception = assertThrows(TalKakException.class, () -> {
            commentService.createComment(memberId, videoId, commentRequest);
        });
        assertEquals("C002", exception.code());
    }

    @Test
    public void testCreateComment_InvalidVideo() {
        // Given
        Long memberId = 1L;
        Long videoId = 1L;
        CommentRequest commentRequest = new CommentRequest("This is a comment.");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(videoRepository.findById(videoId)).thenReturn(Optional.empty());

        // When & Then
        TalKakException exception = assertThrows(TalKakException.class, () -> {
            commentService.createComment(memberId, videoId, commentRequest);
        });
        assertEquals("C003", exception.code());
    }

    @Test
    public void testGetCommentsByVideoId() {
        // Given
        Long videoId = 1L;

        when(commentRepository.findByVideoId(videoId)).thenReturn(Collections.singletonList(comment));
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        // When
        List<CommentResponse> responses = commentService.getCommentsByVideoId(videoId);

        // Then
        assertEquals(1, responses.size());
        assertEquals(comment.getContent(), responses.get(0).content());
        assertEquals(member.getId(), responses.get(0).member().memberId());
    }

    @Test
    public void testUpdateComment() {
        // Given
        Long commentId = 1L;
        Long memberId = 1L;
        CommentRequest commentRequest = new CommentRequest("Updated comment content.");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(commentRepository.save(comment)).thenReturn(comment);

        // When
        CommentResponse response = commentService.updateComment(commentId, memberId, commentRequest);

        // Then
        assertEquals(commentRequest.content(), response.content());
        verify(commentRepository).save(comment);
    }

    @Test
    public void testUpdateComment_InvalidComment() {
        // Given
        Long commentId = 1L;
        Long memberId = 1L;
        CommentRequest commentRequest = new CommentRequest("Updated comment content.");

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // When & Then
        TalKakException exception = assertThrows(TalKakException.class, () -> {
            commentService.updateComment(commentId, memberId, commentRequest);
        });
        assertEquals("C004", exception.code());
    }

    @Test
    public void testUpdateComment_UnauthorizedUser() {
        // Given
        Long commentId = 1L;
        Long memberId = 2L;
        CommentRequest commentRequest = new CommentRequest("Updated comment content.");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(memberRepository.findById(memberId)).thenReturn(
            Optional.of(new Member(2L, "anotherUser")));

        // When & Then
        TalKakException exception = assertThrows(TalKakException.class, () -> {
            commentService.updateComment(commentId, memberId, commentRequest);
        });
        assertEquals("C001", exception.code());
    }

    @Test
    public void testDeleteComment() {
        // Given
        Long commentId = 1L;
        Long memberId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // When
        commentService.deleteComment(commentId, memberId);

        // Then
        verify(commentRepository).delete(comment);
    }

    @Test
    public void testDeleteComment_InvalidComment() {
        // Given
        Long commentId = 1L;
        Long memberId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // When & Then
        TalKakException exception = assertThrows(TalKakException.class, () -> {
            commentService.deleteComment(commentId, memberId);
        });
        assertEquals("C004", exception.code());
    }

    @Test
    public void testDeleteComment_UnauthorizedUser() {
        // Given
        Long commentId = 1L;
        Long memberId = 2L; // Different member

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(memberRepository.findById(memberId)).thenReturn(
            Optional.of(new Member(2L, "anotherUser")));

        // When & Then
        TalKakException exception = assertThrows(TalKakException.class, () -> {
            commentService.deleteComment(commentId, memberId);
        });
        assertEquals("C001", exception.code());
    }
}