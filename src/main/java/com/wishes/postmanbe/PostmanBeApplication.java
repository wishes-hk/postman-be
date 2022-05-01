package com.wishes.postmanbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PostmanBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostmanBeApplication.class, args);
    }

}
