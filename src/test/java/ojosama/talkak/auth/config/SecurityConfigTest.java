package ojosama.talkak.auth.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(SecurityMockMvcConfigurers.springSecurity())
                    .build();
    }

    @Test
    @DisplayName("CORS 설정 테스트")
    void corsConfiguration() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(options("/")
                    .header("Origin", "https://somewhere.test")
                    .header("Access-Control-Request-Method", "GET")
                    .header("Access-Control-Request-Headers", "Test-Header")
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "https://somewhere.test"))
                .andExpect(header().string("Access-Control-Allow-Methods", "GET"))
                .andExpect(header().string("Access-Control-Allow-Headers", "Test-Header"))
                .andExpect(header().string("Access-Control-Expose-Headers", "*"));
        });
    }
}