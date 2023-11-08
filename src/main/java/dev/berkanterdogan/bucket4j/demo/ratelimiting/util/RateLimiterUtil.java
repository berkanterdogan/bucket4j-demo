package dev.berkanterdogan.bucket4j.demo.ratelimiting.util;

import dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.model.RateLimit;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BandwidthBuilder;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConfigurationBuilder;

import java.time.Duration;
import java.util.List;

public class RateLimiterUtil {
    public static BucketConfiguration rateLimitsToBucketConfiguration(List<RateLimit> limits) {
        ConfigurationBuilder configBuilder = BucketConfiguration.builder();
        limits.forEach(limiter -> configBuilder.addLimit(buildBandwidth(limiter)));
        return configBuilder.build();
    }

    private static Bandwidth buildBandwidth(RateLimit rateLimit) {
        String timeUnit = rateLimit.getTimeUnit();
        Long timeValue = rateLimit.getTimeValue();
        Long capacity = rateLimit.getCapacity();

        Bandwidth bandwidth;
        if ("seconds".equalsIgnoreCase(timeUnit)) {
            bandwidth = BandwidthBuilder.builder()
                    .capacity(capacity)
                    .refillIntervally(capacity, Duration.ofSeconds(timeValue))
                    .build();
        } else if ("minutes".equalsIgnoreCase(timeUnit)) {
            bandwidth = BandwidthBuilder.builder()
                    .capacity(capacity)
                    .refillIntervally(capacity, Duration.ofMinutes(timeValue))
                    .build();
        } else if ("hours".equalsIgnoreCase(timeUnit)) {
            bandwidth = BandwidthBuilder.builder()
                    .capacity(capacity)
                    .refillIntervally(capacity, Duration.ofHours(timeValue))
                    .build();
        } else if ("days".equalsIgnoreCase(timeUnit)) {
            bandwidth = BandwidthBuilder.builder()
                    .capacity(capacity)
                    .refillIntervally(capacity, Duration.ofDays(timeValue))
                    .build();
        } else {
            bandwidth = BandwidthBuilder.builder()
                    .capacity(100)
                    .refillIntervally(100, Duration.ofHours(timeValue))
                    .build();
        }

        return bandwidth;

    }
}
