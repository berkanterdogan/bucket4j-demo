package dev.berkanterdogan.bucket4j.demo.ratelimiting.service.business;

public interface RateLimitService {
    String RATE_LIMITER_BUCKET_MAP = "RATE_LIMITER_BUCKET_MAP";

    void controlRateLimit(String servletPath, String ip);
}
