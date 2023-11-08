package dev.berkanterdogan.bucket4j.demo.ratelimiting.service.business.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.RateLimiterPropertyConfigData;
import dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.model.RateLimit;
import dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.model.RateLimiterObjectValue;
import dev.berkanterdogan.bucket4j.demo.ratelimiting.exception.RateLimitException;
import dev.berkanterdogan.bucket4j.demo.ratelimiting.service.business.RateLimitService;
import dev.berkanterdogan.bucket4j.demo.ratelimiting.util.RateLimiterUtil;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.hazelcast.HazelcastLockBasedProxyManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RateLimitServiceImpl implements RateLimitService {


    @Value("${server.port}")
    private String port;

    private final RateLimiterPropertyConfigData rateLimiterPropertyConfigData;

    private final ProxyManager<String> proxyManager;

    public RateLimitServiceImpl(HazelcastInstance hazelcastClient,
                                RateLimiterPropertyConfigData rateLimiterPropertyConfigData) {
        this.rateLimiterPropertyConfigData = rateLimiterPropertyConfigData;
        IMap<String, byte[]> rateLimiterBucketMap = hazelcastClient.getMap(RATE_LIMITER_BUCKET_MAP);
        this.proxyManager = new HazelcastLockBasedProxyManager<>(rateLimiterBucketMap);
    }


    @Override
    public void controlRateLimit(String servletPath, String ip) {
        RateLimiterObjectValue rateLimiterObjectValue = rateLimiterPropertyConfigData.mapToRateLimiterObjectValue();
        List<RateLimit> rateLimitList = rateLimiterObjectValue.getRateLimitList(servletPath);

        String rateLimiterKey = ip + "_" + servletPath;
        log.info("PORT: {}, Rate Limiter Key: {}", port, rateLimiterKey);
        Bucket bucket = proxyManager.builder().build(rateLimiterKey, () -> RateLimiterUtil.rateLimitsToBucketConfiguration(rateLimitList));
        log.info("PORT: {}, Available Tokens: {}", port, bucket.getAvailableTokens());

        if (!bucket.tryConsume(1)) {
            throw new RateLimitException();
        }
    }
}
