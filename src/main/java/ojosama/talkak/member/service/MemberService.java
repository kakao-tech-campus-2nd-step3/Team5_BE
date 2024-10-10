package ojosama.talkak.member.service;

import lombok.RequiredArgsConstructor;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.MemberError;
import ojosama.talkak.member.dto.AdditionalInfoRequest;
import ojosama.talkak.member.model.Member;
import ojosama.talkak.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void updateAdditionalInfo(Long id, AdditionalInfoRequest request) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> TalKakException.of(MemberError.NOT_EXISTING_MEMBER));

    }
}
