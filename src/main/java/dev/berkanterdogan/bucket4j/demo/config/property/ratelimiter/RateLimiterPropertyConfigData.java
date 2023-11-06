package dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter;

import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.model.RateLimiterObjectValue;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bucket4j-demo.rate-limiter")
public class RateLimiterPropertyConfigData {

    private static RateLimiterObjectValue RATE_LIMITER_OBJECT_VALUE;

    private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

    private String objectValue;

    @SneakyThrows
    public RateLimiterObjectValue mapToRateLimiterObjectValue() {
        if (RATE_LIMITER_OBJECT_VALUE == null) {
            RATE_LIMITER_OBJECT_VALUE = JSON_MAPPER.readValue(objectValue, RateLimiterObjectValue.class);
        }
        return RATE_LIMITER_OBJECT_VALUE;
    }


}
