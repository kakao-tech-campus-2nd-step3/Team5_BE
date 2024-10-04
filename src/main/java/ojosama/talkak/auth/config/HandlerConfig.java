package ojosama.talkak.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ojosama.talkak.auth.filter.SuccessHandler;
import ojosama.talkak.auth.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerConfig {

    @Bean
    public SuccessHandler successHandler(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        return new SuccessHandler(jwtUtil, objectMapper);
    }
}
