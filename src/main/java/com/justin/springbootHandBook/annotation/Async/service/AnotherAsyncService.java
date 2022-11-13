package com.justin.springbootHandBook.annotation.Async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: Justin.Luo
 */
@Slf4j
@Service
public class AnotherAsyncService {

    @Async
    public void step4(){
        log.info("step 4");
    }

    @Async("asyncExecutor")
    public void step5(){
        log.info("step 5");
    }

    @Async("fixedThreadPool")
    public void step6(){
        log.info("step 6");
    }

    @Async("fixedThreadPool")
    public void stepError(){
        log.info("Error. {}", 5/0);
    }

    @Async("asyncExecutor")
    public void stepError2(){
        throw new NullPointerException();
    }
}
