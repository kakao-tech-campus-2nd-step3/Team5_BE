package ojosama.talkak.reaction.service;

import ojosama.talkak.member.model.Member;
import ojosama.talkak.reaction.model.Reaction;
import ojosama.talkak.reaction.model.ReactionId;
import ojosama.talkak.reaction.repository.ReactionRepository;
import ojosama.talkak.member.repository.MemberRepository;
import ojosama.talkak.video.model.Video;
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

    public Reaction addOrUpdateReaction(Long memberId, Long videoId, boolean reactionType) {
        ReactionId reactionId = new ReactionId(memberId, videoId);
        Reaction reaction = reactionRepository.findById(reactionId)
            .orElse(new Reaction());
        Member member = memberRepository.findById(memberId).orElseThrow();
        Video video = videoRepository.findById(videoId).orElseThrow();
        reaction.setMember(member);
        reaction.setVideo(video);
        reaction.setReaction(reactionType);

        return reactionRepository.save(reaction);
    }

    public void deleteReaction(Long memberId, Long videoId) {
        reactionRepository.deleteById(new ReactionId(memberId, videoId));
    }

}
