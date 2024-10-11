package ojosama.talkak.member.service;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ojosama.talkak.category.domain.Category;
import ojosama.talkak.category.domain.CategoryType;
import ojosama.talkak.category.domain.MemberCategory;
import ojosama.talkak.category.repository.CategoryRepository;
import ojosama.talkak.category.repository.MemberCategoryRepository;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.CategoryError;
import ojosama.talkak.common.exception.code.MemberError;
import ojosama.talkak.member.domain.Member;
import ojosama.talkak.member.dto.AdditionalInfoRequest;
import ojosama.talkak.member.dto.AdditionalInfoResponse;
import ojosama.talkak.member.dto.MyPageInfoRequest;
import ojosama.talkak.member.dto.MyPageInfoResponse;
import ojosama.talkak.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberCategoryRepository memberCategoryRepository;
    private final CategoryRepository categoryRepository;

    public static final Integer ALLOWED_CATEGORY_SELECT_COUNT = 3;

    public MemberService(MemberRepository memberRepository,
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

    @Transactional
    public AdditionalInfoResponse updateAdditionalInfo(Long memberId, AdditionalInfoRequest request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> TalKakException.of(MemberError.NOT_EXISTING_MEMBER));
        List<Category> categories = new ArrayList<>();

        member.updateMemberInfo(request.gender(), request.age());

        request.categories()
            .stream()
            .map(CategoryType::fromName)
            .forEach(categoryType -> {
                Category category = categoryRepository.findByCategoryType(categoryType)
                    .orElseThrow(() -> TalKakException.of(CategoryError.NOT_EXISTING_CATEGORY));
                categories.add(category);
                memberCategoryRepository.save(MemberCategory.of(member, category));
            });

        return AdditionalInfoResponse.of(categories, request);
    }

    public MyPageInfoResponse updateMemberInfo(Long memberId, MyPageInfoRequest request) {
        // 유저 성별, 나이 검증
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> TalKakException.of(MemberError.NOT_EXISTING_MEMBER));
        member.updateMemberInfo(request.gender(), request.age());

        //유효한 카테고리 입력인지 사전 검증(카테고리 허용 개수와 일치하는지, 서로 다른 카테고리인지, 각각 존재하는 카테고리인지?)
        Set<Long> newDistinctCategoryIds = categoryRepository.findExistingIds(new HashSet<>(request.categories()));
        Category.validateCategoryInputs(newDistinctCategoryIds);

        // 새로 변경되는 카테고리 리스트에 존재하지 않는 기존 카테고리를 리스트에서 삭제
        List<MemberCategory> memberCategories = memberCategoryRepository.findAllByMemberId(
            memberId);
        memberCategories.removeIf(
            mc -> request.categories().stream().noneMatch(
                c -> c.equals(mc.getCategory().getId())
            )
        );

        // 새롭게 추가되는 카테고리가 무엇인지 찾고, 새롭게 추가되는 카테고리를 리스트에 추가
        List<Long> newCategoryIds = newDistinctCategoryIds.stream()
            .filter(
                c -> memberCategories.stream().noneMatch(
                    mc -> mc.getCategory().getId().equals(c)
                )
            ).toList();
        List<MemberCategory> newMemberCategories = categoryRepository.findAllByCategoryIds(newCategoryIds)
            .stream()
            .map(c -> memberCategoryRepository.save(new MemberCategory(member, c)))
            .toList();
        memberCategories.addAll(newMemberCategories);

        return MyPageInfoResponse.of(member,
            memberCategories.stream().map(MemberCategory::getCategory).toList());
    }
}
