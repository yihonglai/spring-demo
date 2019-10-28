package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(GreetingRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Greeting("Hello","New", "world")));
            log.info("Preloading " + repository.save(new Greeting("Hi","New", "world")));
        };
    }
}
