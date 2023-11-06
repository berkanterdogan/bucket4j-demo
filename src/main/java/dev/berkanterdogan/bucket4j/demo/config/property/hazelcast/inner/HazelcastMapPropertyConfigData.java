package dev.berkanterdogan.bucket4j.demo.config.property.hazelcast.inner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HazelcastMapPropertyConfigData {

    private HazelcastNearCachePropertyConfigData nearCache;
    private Integer timeToLiveSecs;
    private Integer maxIdleSecs;

}
