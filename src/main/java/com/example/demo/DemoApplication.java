package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Точка входа.
 *
 * @author kanenkovaa
 * @version 0.1
 */
@EnableScheduling
@SpringBootApplication
public class DemoApplication {

    /**
     * Запуск приложения
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactoryBean() {
        return new LettuceConnectionFactory("redis", 6379);
    }

}
