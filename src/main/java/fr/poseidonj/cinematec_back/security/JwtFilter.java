package fr.poseidonj.cinematec_back.security;

import fr.poseidonj.cinematec_back.exception.ExpiredTokenException;
import fr.poseidonj.cinematec_back.exception.IllegalTokenException;
import fr.poseidonj.cinematec_back.utilities.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {

    protected final UserDetailsService service;
    private final JwtUtil jwtUtil;

    @Lazy
    public JwtFilter(UserDetailsService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws IllegalTokenException, ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer")) {
            token = authorizationHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                throw new IllegalTokenException();
            } catch (ExpiredJwtException e) {
                throw new ExpiredTokenException();
            }
        } else if (authorizationHeader != null) {
            throw new IllegalTokenException();
        }

        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext())) {
            UserDetails userDetails = service.loadUserByUsername(username);

            if (Objects.nonNull(userDetails) && Boolean.TRUE.equals(jwtUtil.validateToken(token, userDetails))) {
                UsernamePasswordAuthenticationToken userToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(userToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
