package ru.otus.hw.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class ClientConfig {

    private String username = "User %s : %s";

    // getters and setters
}
