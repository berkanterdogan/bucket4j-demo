package dev.berkanterdogan.bucket4j.demo.config.property.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RateLimit {
    private String timeUnit;
    private Long timeValue;
    private Long capacity;


}