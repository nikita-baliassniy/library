package ru.geekbrains.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LibApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibApplication.class, args);
    }
}
