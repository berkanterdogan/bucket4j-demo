package dev.berkanterdogan.bucket4j.demo.ratelimiting.filter;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.RateLimiterPropertyConfigData;
import dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.model.RateLimit;
import dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.model.RateLimiterObjectValue;
import dev.berkanterdogan.bucket4j.demo.ratelimiting.util.RateLimiterUtil;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.hazelcast.HazelcastLockBasedProxyManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final String RATE_LIMITER_BUCKET_MAP = "RATE_LIMITER_BUCKET_MAP";

    @Value("${server.port}")
    private String port;

    private final RateLimiterPropertyConfigData rateLimiterPropertyConfigData;

    private final ProxyManager<String> proxyManager;

    public RateLimitingFilter(HazelcastInstance hazelcastClient,
                              RateLimiterPropertyConfigData rateLimiterPropertyConfigData) {
        this.rateLimiterPropertyConfigData = rateLimiterPropertyConfigData;
        IMap<String, byte[]> rateLimiterBucketMap = hazelcastClient.getMap(RATE_LIMITER_BUCKET_MAP);
        this.proxyManager = new HazelcastLockBasedProxyManager<>(rateLimiterBucketMap);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        String servletPath = request.getServletPath();

        RateLimiterObjectValue rateLimiterObjectValue = rateLimiterPropertyConfigData.mapToRateLimiterObjectValue();
        List<RateLimit> rateLimitList = rateLimiterObjectValue.getRateLimitList(servletPath);

        String rateLimiterKey = ip + "_" + servletPath;
        log.info("PORT: {}, Rate Limiter Key: {}", port, rateLimiterKey);
        Bucket bucket = proxyManager.builder().build(rateLimiterKey, () -> RateLimiterUtil.rateLimitsToBucketConfiguration(rateLimitList));
        log.info("PORT: {}, Available Tokens: {}", port, bucket.getAvailableTokens());

        if (!bucket.tryConsume(1)) {
            response.setStatus(429);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
