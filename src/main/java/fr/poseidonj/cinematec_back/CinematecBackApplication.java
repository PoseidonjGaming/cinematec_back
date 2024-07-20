package fr.poseidonj.cinematec_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CinematecBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinematecBackApplication.class, args);
    }

}
