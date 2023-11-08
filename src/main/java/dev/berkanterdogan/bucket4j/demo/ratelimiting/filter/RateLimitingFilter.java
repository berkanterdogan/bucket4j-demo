package dev.berkanterdogan.bucket4j.demo.ratelimiting.filter;

import dev.berkanterdogan.bucket4j.demo.ratelimiting.exception.RateLimitException;
import dev.berkanterdogan.bucket4j.demo.ratelimiting.service.business.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class RateLimitingFilter extends OncePerRequestFilter {


    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        String servletPath = request.getServletPath();

        try {
            this.rateLimitService.controlRateLimit(ip, servletPath);
        } catch (RateLimitException e) {
            response.setStatus(e.getHttpStatusCode().value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}

