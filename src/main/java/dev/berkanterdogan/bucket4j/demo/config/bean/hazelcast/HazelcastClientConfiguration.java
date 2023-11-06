package dev.berkanterdogan.bucket4j.demo.config.bean.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import dev.berkanterdogan.bucket4j.demo.config.property.hazelcast.HazelcastPropertyConfigData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HazelcastClientConfiguration {

    private final HazelcastPropertyConfigData hazelcastPropertyConfigData;

    @Bean
    public HazelcastInstance hazelcastClient() {
        return HazelcastClient.newHazelcastClient(createClientConfig());
    }

    private ClientConfig createClientConfig() {
        ClientConfig clientConfig = new ClientConfig();

        clientConfig.setClusterName(this.hazelcastPropertyConfigData.getClusterName());
        ClientNetworkConfig clientNetworkConfig = clientConfig.getNetworkConfig();
        clientNetworkConfig.addAddress(this.hazelcastPropertyConfigData.convertUrlsToArray());

        return clientConfig;
    }
}
