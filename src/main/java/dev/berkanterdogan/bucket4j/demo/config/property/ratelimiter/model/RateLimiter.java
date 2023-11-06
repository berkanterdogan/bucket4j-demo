package dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RateLimiter {
    private String path;
    private List<RateLimit> limits;

}