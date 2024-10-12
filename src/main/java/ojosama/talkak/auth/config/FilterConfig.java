package ojosama.talkak.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ojosama.talkak.auth.filter.JwtAuthorizationFilter;
import ojosama.talkak.auth.filter.SuccessHandler;
import ojosama.talkak.auth.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public SuccessHandler successHandler(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        return new SuccessHandler(jwtUtil, objectMapper);
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(JwtUtil jwtUtil) {
        return new JwtAuthorizationFilter(jwtUtil);
    }
}
