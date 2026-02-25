package com.sync.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfig {

    public static final String VIRTUAL_TASK_EXECUTOR = "virtualTaskExecutor";
    public static final String PLATFORM_TASK_EXECUTOR = "platformTaskExecutor";

    @Bean(name = VIRTUAL_TASK_EXECUTOR)
    public Executor taskExecutor() {
        // Cria um executor que inicia uma Virtual Thread para cada tarefa
        return Executors.newThreadPerTaskExecutor(
                Thread.ofVirtual().name("vthread-", 0).factory()
        );
    }

    @Bean(name = PLATFORM_TASK_EXECUTOR)
    public Executor platformTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);           // ajuste conforme sua máquina/necessidade
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("pthread-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        return executor;
    }
}
