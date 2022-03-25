package com.example.demo.Security;

import com.example.demo.Exception.TokenFormateNotValidException;
import com.example.demo.utils.Jwtutilsproviders;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@CrossOrigin
public class JwtOncePerRequestFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService customeuserDetailsService;

    @Autowired
    private Jwtutilsproviders jwtutilsproviders;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String full_token = request.getHeader("Authorization");

        String username = null;
        String token = null;
        UserDetails userDetails;

        if (full_token != null && full_token.startsWith("Bearer ")) {
            token = full_token.substring(7, full_token.length());

            try {
                username = jwtutilsproviders.getUsernameFromToken(token);

            } catch (Exception e) {
                throw new TokenFormateNotValidException("token is either null or does not contains bearer before token");
            }

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            userDetails = customeuserDetailsService.loadUserByUsername(username);

            if (jwtutilsproviders.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
            }
        };
    }
}
