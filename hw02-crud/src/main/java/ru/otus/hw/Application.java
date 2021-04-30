package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ru.otus.hw")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
