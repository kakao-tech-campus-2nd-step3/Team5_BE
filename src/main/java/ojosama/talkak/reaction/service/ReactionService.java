package ojosama.talkak.reaction.service;

import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.ReactionError;
import ojosama.talkak.member.domain.Member;
import ojosama.talkak.member.repository.MemberRepository;
import ojosama.talkak.reaction.domain.Reaction;
import ojosama.talkak.reaction.domain.ReactionId;
import ojosama.talkak.reaction.repository.ReactionRepository;
import ojosama.talkak.video.domain.Video;
import ojosama.talkak.video.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;

    public ReactionService(ReactionRepository reactionRepository, MemberRepository memberRepository,
        VideoRepository videoRepository) {
        this.reactionRepository = reactionRepository;
        this.memberRepository = memberRepository;
        this.videoRepository = videoRepository;
    }

    public void toggleLike(Long videoId, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> TalKakException.of(ReactionError.INVALID_MEMBER_ID));
        Video video = videoRepository.findById(videoId)
            .orElseThrow(() -> TalKakException.of(ReactionError.INVALID_VIDEO_ID));

        ReactionId reactionId = new ReactionId(memberId, videoId);
        Reaction existingReaction = reactionRepository.findById(reactionId)
            .orElse(null);

        if (existingReaction != null) {
            handleExistingReaction(existingReaction, video);
        } else {
            Reaction newReaction = new Reaction(reactionId, member, video, true);
            reactionRepository.save(newReaction);
            video.incrementLikes();
        }
        videoRepository.save(video);
    }

    private void handleExistingReaction(Reaction existingReaction, Video video) {
        reactionRepository.delete(existingReaction);
        video.decrementLikes();
    }
}
