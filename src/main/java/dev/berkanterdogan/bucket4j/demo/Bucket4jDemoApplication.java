package dev.berkanterdogan.bucket4j.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Bucket4jDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Bucket4jDemoApplication.class, args);
    }

}
