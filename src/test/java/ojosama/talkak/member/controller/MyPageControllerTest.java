package ojosama.talkak.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;
import ojosama.talkak.category.domain.Category;
import ojosama.talkak.category.domain.CategoryType;
import ojosama.talkak.member.domain.Member;
import ojosama.talkak.member.dto.MyPageInfoRequest;
import ojosama.talkak.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class MyPageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberService memberService;

    Member member;
    List<Category> categories;
    List<CategoryType> categoriesNames = Arrays.asList(CategoryType.values());

    @BeforeEach
    void setUp() {

    }

    @DisplayName("유효하지 않은 마이페이지 수정 Request: 성별")
    @Test
    void invalid_input_gender() throws Exception {
        String content = objectMapper.writeValueAsString(createGender(null));
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/me")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("유효하지 않은 마이페이지 수정 Request: 나이")
    @Test
    void invalid_input_age() throws Exception {
        String content = objectMapper.writeValueAsString(createAge(null));
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/me")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("유효하지 않은 마이페이지 수정 Request: 카테고리")
    @Test
    void invalid_input_category() throws Exception {
        String content = objectMapper.writeValueAsString(createCategories(null));
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/me")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(print());

        String content2 = objectMapper.writeValueAsString(createCategories(List.of("음악")));
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/me")
                    .content(content2)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(print());

        String content3 = objectMapper.writeValueAsString(createCategories(List.of("음악", "음식", "여행", "게임")));
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/me")
                    .content(content2)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(print());
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

    List<Long> demoCategoryIds(List<String> categoryNames) {
        if (categoryNames == null || categoryNames.isEmpty()) {
            return null;
        }
        return LongStream.range(0, categoryNames.size())
            .boxed().toList();
    }

}