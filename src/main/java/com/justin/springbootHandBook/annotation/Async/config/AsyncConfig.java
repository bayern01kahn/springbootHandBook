package com.justin.springbootHandBook.annotation.Async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: Justin.Luo
 */
@Configuration
public class AsyncConfig {

    private static final int MAX_POOL_SIZE = 50;
    public static final int CORE_POOL_SIZE = 20;

    /**
     * customize async Executor threadpool.
     * @return
     */
    @Bean("asyncExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor async = new ThreadPoolTaskExecutor();

        async.setMaxPoolSize(MAX_POOL_SIZE);
        async.setCorePoolSize(CORE_POOL_SIZE);
        async.setThreadNamePrefix("async-threads-");
        async.setWaitForTasksToCompleteOnShutdown(false);
        async.setQueueCapacity(100);

        async.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return async;
    }


    /**
     * use juc newFixedThreadPool
     * @return
     */
    @Bean("fixedThreadPool")
    public ExecutorService myFixedThreadPool() {
        int processors = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(processors * 5);
    }
}
