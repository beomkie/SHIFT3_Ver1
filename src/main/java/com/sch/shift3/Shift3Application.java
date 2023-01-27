package com.sch.shift3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Shift3Application {

    public static void main(String[] args) {
        SpringApplication.run(Shift3Application.class, args);
    }

}
