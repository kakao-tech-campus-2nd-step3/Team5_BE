package ojosama.talkak.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import ojosama.talkak.auth.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException
    {
        String headerValue = request.getHeader("Authorization");

        if (isValidToken(headerValue)) {
            SecurityContextHolder.getContext()
                .setAuthentication(
                    createUsernamePasswordAuthenticationToken(headerValue.split(" ")[1]));
        }

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(String token) {
        Long id = jwtUtil.getIdFromToken(token);
        return new UsernamePasswordAuthenticationToken(id, null, new ArrayList<>());
    }

    private boolean isValidToken(String value) {
        return !Objects.isNull(value) &&
            value.startsWith("Bearer ") &&
            jwtUtil.isValidToken(value.split(" ")[1]);
    }
}
