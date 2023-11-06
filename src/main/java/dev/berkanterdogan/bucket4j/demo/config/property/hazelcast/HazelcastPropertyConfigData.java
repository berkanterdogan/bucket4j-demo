package dev.berkanterdogan.bucket4j.demo.config.property.hazelcast;

import dev.berkanterdogan.bucket4j.demo.config.property.hazelcast.inner.HazelcastMapPropertyConfigData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bucket4j-demo.hazelcast")
public class HazelcastPropertyConfigData {

    private String clusterName;
    private List<String> urls;
    private HazelcastMapPropertyConfigData map;

    public String[] convertUrlsToArray() {
        String[] urlArray = new String[this.urls.size()];
        urls.toArray(urlArray);

        return urlArray;
    }
}
