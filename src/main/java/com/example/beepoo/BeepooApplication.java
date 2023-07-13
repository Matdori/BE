package com.example.beepoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeepooApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeepooApplication.class, args);
    }

}
