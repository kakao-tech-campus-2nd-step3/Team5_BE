package ojosama.talkak.member.service;

import static ojosama.talkak.common.exception.ExceptionAssertions.assertErrorCode;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ojosama.talkak.category.domain.Category;
import ojosama.talkak.category.domain.CategoryType;

import ojosama.talkak.category.domain.MemberCategory;
import ojosama.talkak.category.repository.CategoryRepository;
import ojosama.talkak.category.repository.MemberCategoryRepository;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.MemberError;
import ojosama.talkak.member.dto.MyPageInfoRequest;
import ojosama.talkak.member.dto.MyPageInfoResponse;
import ojosama.talkak.member.dto.MyPageInfoResponse.CategoryResponse;
import ojosama.talkak.member.domain.Age;
import ojosama.talkak.member.domain.Member;
import ojosama.talkak.member.domain.MembershipTier;
import ojosama.talkak.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MyPageServiceTest {

    @Autowired
    private MyPageService myPageService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberCategoryRepository memberCategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    Member member;
    List<Category> categories;
    List<CategoryType> categoriesNames = Arrays.asList(CategoryType.values());

    /***
     * 초기 회원 정보
     * 성별: 남자
     * 나이: 10대
     * 선택 카테고리: 음식, 여행, 게임
     */

    @BeforeEach
    void before() {
        member = demoMember();
        memberRepository.save(member);

        List<Category> temp = categoriesNames.stream()
            .map(cn -> new Category(null, cn))
            .toList();
        categories = categoryRepository.saveAll(temp);
        System.out.println("출력: " + CategoryType.valueOf("FOOD"));
        List<MemberCategory> memberCategories = categories.stream()
            .map(c -> new MemberCategory(member, c))
            .limit(3)
            .toList();

        memberCategoryRepository.saveAll(memberCategories);
    }

    @DisplayName("마이페이지 개인정보 가져오기")
    @Test
    void get_myPage_info() {
        MyPageInfoResponse memberInfo = myPageService.getMemberInfo(member.getId());

        assertThat(memberInfo.gender()).isEqualTo("남자");
        assertThat(memberInfo.age()).isEqualTo("20대");
        assertThat(memberInfo.categories().size()).isEqualTo(3);
    }

    /***
     * 회원 변경 정보
     * 성별: 남자 -> 여자
     * 나이: 10대 -> 20대
     * 선택 카테고리: 음식, 여행, 게임 -> 음식, 음악, 스포츠
     */

    @DisplayName("마이페이지 개인정보 수정하기-성공")
    @Test
    void update_myPage_info() {
        MyPageInfoRequest request = new MyPageInfoRequest("여자", "20대",
            demoCategoryIds(Arrays.asList("음식", "음악", "스포츠")));
        MyPageInfoResponse memberInfo = myPageService.updateMemberInfo(member.getId(), request);
        List<String> categoryNames = memberInfo.categories().stream()
            .map(CategoryResponse::name)
            .toList();

        assertThat(memberInfo.gender()).isEqualTo("여자");
        assertThat(memberInfo.age()).isEqualTo("20대");
        assertThat(categoryNames.size()).isEqualTo(3);
        assertThat(categoryNames).containsOnly("음식", "음악", "스포츠");
    }

    @DisplayName("마이페이지 개인정보 수정하기 실패-유효하지 않은 성별 정보")
    @Test
    void invalid_input_gender() {
        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createGender(null)));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createGender("")));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createGender("남자 ")));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createGender("남자, 여자")));
    }

    @DisplayName("마이페이지 개인정보 수정하기 실패-유효하지 않은 나이 정보")
    @Test
    void invalid_input_age() {
        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createAge(null)));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createAge("")));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createAge("10")));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createAge("10대 ")));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createAge("10대.")));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createAge("60대")));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(), createAge("50대이상")));
    }

    @DisplayName("마이페이지 개인정보 수정하기 실패-유효하지 않은 카테고리 옵션")
    @Test
    void invalid_input_categories() {
        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(),
                createCategories(List.of("", "", ""))));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(),
                createCategories(List.of("음식"))));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(),
                createCategories(List.of("음식", "음악"))));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(),
                createCategories(List.of("음식", "음악", "여행", "게임"))));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(),
                createCategories(List.of("음식", "음악", "음악"))));

        assertErrorCode(MemberError.ERROR_UPDATE_MEMBER_INFO,
            () -> myPageService.updateMemberInfo(member.getId(),
                createCategories(List.of("음식", "음악", "여행 "))));
    }

    private MyPageInfoRequest createGender(String gender) {
        return new MyPageInfoRequest(gender, "20대",
            demoCategoryIds(Arrays.asList("음식", "음악", "스포츠")));
    }

    private MyPageInfoRequest createAge(String age) {
        return new MyPageInfoRequest("남자", age,
            demoCategoryIds(Arrays.asList("음식", "음악", "스포츠")));
    }

    private MyPageInfoRequest createCategories(List<String> categories) {
        return new MyPageInfoRequest("남자", "10대", demoCategoryIds(categories));
    }

    private static Member demoMember() {
        return new Member(null, "철수 김", "https://",
            "abc123@a.com", false, Age.TWENTY, MembershipTier.Basic, 0, new ArrayList<>());
    }

    List<Long> demoCategoryIds(List<String> categoryNames) {
        return categoryNames.stream()
            .map(cn -> categoryRepository.findByCategoryType(CategoryType.fromName(cn))
                .orElseThrow(() -> TalKakException.of(MemberError.ERROR_UPDATE_MEMBER_INFO)))
            .map(Category::getId)
            .toList();
    }

}