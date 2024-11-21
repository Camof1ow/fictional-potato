package com.example.japanesenamegenerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig
{
    @Bean(name = "AsyncThreadExecutor")
    public Executor asyncThreadExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);  //기본 쓰레드풀의 쓰레드 개수
        executor.setMaxPoolSize(8); //최대 쓰레드풀의 쓰레드 개수. [queueCapacity]가 꽉차면 maxPoolSize만큼 넓혀감.
        executor.setQueueCapacity(100); //쓰레드 대기큐.
        executor.setThreadNamePrefix("forktest-");
        executor.initialize();
        return executor;
    }

}
