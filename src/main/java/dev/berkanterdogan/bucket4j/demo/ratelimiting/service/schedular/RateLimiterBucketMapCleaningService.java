package dev.berkanterdogan.bucket4j.demo.ratelimiting.service.schedular;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import dev.berkanterdogan.bucket4j.demo.ratelimiting.service.business.RateLimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RateLimiterBucketMapCleaningService {

    private final HazelcastInstance hazelcastClient;

    @Scheduled(cron = "@daily")
    public void clearRateLimiterBucketMap() {
        String rateLimiterBucketMapName = RateLimitService.RATE_LIMITER_BUCKET_MAP;
        IMap<String, byte[]> rateLimiterBucketMap = hazelcastClient.getMap(rateLimiterBucketMapName);
        rateLimiterBucketMap.clear();
        log.debug("{} was cleared successfully!", rateLimiterBucketMapName);
    }
}
