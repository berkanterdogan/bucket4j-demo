package dev.berkanterdogan.bucket4j.demo.ratelimiting.util;

import dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.model.RateLimit;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BandwidthBuilder;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConfigurationBuilder;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
        Instant firstRefillTime;
        if ("seconds".equalsIgnoreCase(timeUnit)) {
            firstRefillTime = ZonedDateTime.now()
                    .truncatedTo(ChronoUnit.SECONDS)
                    .plusSeconds(1)
                    .toInstant();
            bandwidth = BandwidthBuilder.builder()
                    .capacity(capacity)
                    .refillIntervallyAligned(capacity, Duration.ofSeconds(timeValue), firstRefillTime)
                    .build();
        } else if ("minutes".equalsIgnoreCase(timeUnit)) {
            firstRefillTime = ZonedDateTime.now()
                    .truncatedTo(ChronoUnit.MINUTES)
                    .plusMinutes(1)
                    .toInstant();
            bandwidth = BandwidthBuilder.builder()
                    .capacity(capacity)
                    .refillIntervallyAligned(capacity, Duration.ofMinutes(timeValue), firstRefillTime)
                    .build();
        } else if ("hours".equalsIgnoreCase(timeUnit)) {
            firstRefillTime = ZonedDateTime.now()
                    .truncatedTo(ChronoUnit.HOURS)
                    .plusHours(1)
                    .toInstant();
            bandwidth = BandwidthBuilder.builder()
                    .capacity(capacity)
                    .refillIntervallyAligned(capacity, Duration.ofHours(timeValue), firstRefillTime)
                    .build();
        } else if ("days".equalsIgnoreCase(timeUnit)) {
            firstRefillTime = ZonedDateTime.now()
                    .truncatedTo(ChronoUnit.DAYS)
                    .plusDays(1)
                    .toInstant();
            bandwidth = BandwidthBuilder.builder()
                    .capacity(capacity)
                    .refillIntervallyAligned(capacity, Duration.ofDays(timeValue), firstRefillTime)
                    .build();
        } else {
            firstRefillTime = ZonedDateTime.now()
                    .truncatedTo(ChronoUnit.HOURS)
                    .plusHours(1)
                    .toInstant();
            bandwidth = BandwidthBuilder.builder()
                    .capacity(100)
                    .refillIntervallyAligned(100, Duration.ofHours(timeValue), firstRefillTime)
                    .build();
        }

        return bandwidth;

    }
}
