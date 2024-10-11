package ojosama.talkak.viewingRecord.service;

import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.MemberError;
import ojosama.talkak.common.exception.code.VideoError;
import ojosama.talkak.member.domain.Member;
import ojosama.talkak.member.repository.MemberRepository;
import ojosama.talkak.video.domain.Video;
import ojosama.talkak.video.repository.VideoRepository;
import ojosama.talkak.viewingRecord.domain.ViewingRecord;
import ojosama.talkak.viewingRecord.repository.ViewingRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ViewingRecordService {

    private final ViewingRecordRepository viewingRecordRepository;
    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;

    public ViewingRecordService(ViewingRecordRepository viewingRecordRepository,
        MemberRepository memberRepository, VideoRepository videoRepository) {
        this.viewingRecordRepository = viewingRecordRepository;
        this.memberRepository = memberRepository;
        this.videoRepository = videoRepository;
    }

    @Transactional
    public void incrementViewsAndCreateRecord(Long videoId, Long memberId) {
        Video video = videoRepository.findById(videoId)
            .orElseThrow(() -> TalKakException.of(VideoError.INVALID_VIDEO_ID));

        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> TalKakException.of(MemberError.NOT_EXISTING_MEMBER));

        viewingRecordRepository.save(new ViewingRecord(member, video));
        video.incrementViews();
    }

}
