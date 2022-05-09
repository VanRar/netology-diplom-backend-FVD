package ru.netology.netologydiplombackendfvd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class NetologyDiplomBackendFvdApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetologyDiplomBackendFvdApplication.class, args);
    }

}
