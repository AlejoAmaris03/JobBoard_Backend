package com.springboot.job_platform.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync

public class AsyncConfig {
    @Bean
    public Executor threadExecutor() {
        ThreadPoolTaskExecutor threads = new ThreadPoolTaskExecutor();
        threads.setCorePoolSize(2);
        threads.setMaxPoolSize(5);
        threads.setQueueCapacity(25);
        threads.setThreadNamePrefix("MyAsync-");
        threads.initialize();

        return threads;
    }
}
