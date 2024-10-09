package ojosama.talkak.member.service;

import java.util.List;
import ojosama.talkak.category.model.Category;
import ojosama.talkak.category.model.MemberCategory;
import ojosama.talkak.category.repository.CategoryRepository;
import ojosama.talkak.category.repository.MemberCategoryRepository;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.CategoryError;
import ojosama.talkak.common.exception.code.MemberError;
import ojosama.talkak.member.dto.MyPageInfoRequest;
import ojosama.talkak.member.dto.MyPageInfoResponse;
import ojosama.talkak.member.model.Member;
import ojosama.talkak.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

    private final MemberRepository memberRepository;
    private final MemberCategoryRepository memberCategoryRepository;
    private final CategoryRepository categoryRepository;

    public MyPageService(MemberRepository memberRepository,
        MemberCategoryRepository memberCategoryRepository, CategoryRepository categoryRepository) {
        this.memberRepository = memberRepository;
        this.memberCategoryRepository = memberCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public MyPageInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> TalKakException.of(MemberError.NOT_EXISTING_MEMBER));
        List<Category> categories = memberCategoryRepository.findAllCategoriesByMember(
            memberId);

        return MyPageInfoResponse.of(member, categories);
    }

    public MyPageInfoResponse updateMemberInfo(Long memberId, MyPageInfoRequest request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> TalKakException.of(MemberError.NOT_EXISTING_MEMBER));
        member.updateMemberInfo(request.gender(), request.age());

        // 새로 변경되는 카테고리 리스트에 존재하지 않는 기존 카테고리를 리스트에서 삭제
        MemberCategory.isValidCategories(request.categories());
        List<MemberCategory> memberCategories = memberCategoryRepository.findAllByMemberId(
            memberId);
        memberCategories.removeIf(
            mc -> request.categories().stream().noneMatch(
                c -> c.equals(mc.getCategory().getId())
            )
        );

        // 새롭게 추가되는 카테고리가 무엇인지 찾고, 새롭게 추가되는 카테고리를 리스트에 추가
        List<MemberCategory> newMemberCategories = request.categories().stream()
            .filter(
                c -> memberCategories.stream().noneMatch(
                    mc -> mc.getCategory().getId().equals(c)
                )
            ).map(c -> {
                Category category = categoryRepository.findById(c)
                    .orElseThrow(() -> TalKakException.of(CategoryError.NOT_EXISTING_CATEGORY));
                return memberCategoryRepository.save(new MemberCategory(member, category));
            })
            .toList();

        memberCategories.addAll(newMemberCategories);

        return MyPageInfoResponse.of(member,
            memberCategories.stream().map(MemberCategory::getCategory).toList());
    }


}
