package dev.berkanterdogan.bucket4j.demo.config.property.hazelcast.inner;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HazelcastNearCachePropertyConfigData {
    private String status;
    private List<String> names;
    private Integer timeToLiveSecs;
    private Integer maxIdleSecs;
}
