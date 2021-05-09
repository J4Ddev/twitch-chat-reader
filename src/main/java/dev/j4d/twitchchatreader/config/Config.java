package dev.j4d.twitchchatreader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class Config {

    @Bean
    public Supplier<UUID> uuid() {
        return UUID::randomUUID;
    }

    @Bean
    public Supplier<Instant> instant() {
        return Instant::now;
    }
}
