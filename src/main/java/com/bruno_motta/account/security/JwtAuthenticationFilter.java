package com.bruno_motta.account.security;

import com.bruno_motta.account.entity.User;
import com.bruno_motta.account.entity.enums.AccountStatus;
import com.bruno_motta.account.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        extractToken(request)
                .filter(tokenService::isTokenValid)
                .map(tokenService::extractSubject)
                .flatMap(userRepository::findByEmail)
                .filter(user -> user.getAccountStatus() == AccountStatus.ACTIVE)
                .ifPresent(user -> authenticate(user, request));

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            return Optional.empty();
        }

        return Optional.of(authorization.substring(BEARER_PREFIX.length()));
    }

    private void authenticate(User user, HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
