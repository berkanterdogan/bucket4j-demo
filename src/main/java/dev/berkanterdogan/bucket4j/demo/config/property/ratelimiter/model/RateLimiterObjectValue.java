package dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RateLimiterObjectValue {

    private static final String DEFAULT_RATE_LIMIT_PATH = "/";

    private List<RateLimiter> limiters;

    public List<RateLimit> getRateLimitList(String path) {
        Optional<List<RateLimit>> rateLimitsOptional = getRateLimitListByPath(path);
        return rateLimitsOptional.orElse(getDefaultRateLimitList());
    }

    private Optional<List<RateLimit>> getRateLimitListByPath(String path) {
        return this.limiters.stream()
                .filter(limiter -> path.equals(limiter.getPath()))
                .map(RateLimiter::getLimits)
                .findFirst();
    }

    private List<RateLimit> getDefaultRateLimitList() {
        return this.limiters.stream()
                .filter(limiter -> DEFAULT_RATE_LIMIT_PATH.equals(limiter.getPath()))
                .map(RateLimiter::getLimits)
                .findFirst().get();
    }

}