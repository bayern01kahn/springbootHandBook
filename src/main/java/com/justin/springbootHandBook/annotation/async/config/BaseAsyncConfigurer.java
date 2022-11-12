package com.justin.springbootHandBook.annotation.async.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Override the @Async Executor at the Application Level
 * @Author: Justin.Luo
 */
@Slf4j
@Configuration
public class BaseAsyncConfigurer implements AsyncConfigurer {

    /**
     * replace default Async Executor with Customize One.
     * @Async 默认异步配置使用的是SimpleAsyncTaskExecutor
     * 该线程池默认来一个任务创建一个线程，若系统中不断的创建线程，最终会导致系统占用内存过高，引发OutOfMemoryError错误。
     * 针对线程创建问题，SimpleAsyncTaskExecutor提供了限流机制，通过concurrencyLimit属性来控制开关，
     * 当concurrencyLimit>=0时开启限流机制，默认关闭限流机制即concurrencyLimit=-1，当关闭情况下，会不断创建新的线程来处理任务。
     * 基于默认配置，SimpleAsyncTaskExecutor并不是严格意义的线程池，达不到线程复用的功能。
     */

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors()*5);
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors()*10);
        executor.setThreadNamePrefix("replacedAsync-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * override the getAsyncUncaughtExceptionHandler() method to return our custom asynchronous exception handler:
     * !!! All the exception happened in @async will been handler here.
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (Throwable ex, Method method, Object... params)->{

            try {
                log.error("\n\n[Exception-Async-Handler] Class-Name: {}-{}\nType: {}\nException: {}\n\n",
                        method.getDeclaringClass().getName(),method.getName(),
                        ex.getClass().getName(),
                        ex.getMessage());
            } catch (Throwable nex) {
                log.error("catch Async Exception: {}", nex);
            }
        };
    }

}