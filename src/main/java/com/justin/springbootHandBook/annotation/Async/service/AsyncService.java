package com.justin.springbootHandBook.annotation.Async.service;

import com.justin.springbootHandBook.common.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @Author: Justin.Luo
 */
@Slf4j
@Service
public class AsyncService {

    @Autowired
    public AnotherAsyncService anotherAsyncService;

    public void all(){
        /**
         * As step1() step2() step3() in same class as all().
         * which will make those 3 steps called by same caller as all() not the proxy caller. then async fail
         */
        step1();
        step2();
        step3();
        /**
         * But as step4() step5() step6() in different class. then 4,5,6 will do async way.
         */
        anotherAsyncService.step4();
        anotherAsyncService.step5();
        anotherAsyncService.step6();
    }

    public void allInSameClass(){
        /**
         *  Methods in the same class need to get the proxy object first, manually get the object
         */
        AsyncService bean = ApplicationContextUtil.getBean(AsyncService.class);
        bean.step1();
        bean.step2();
        bean.step3();
    }

    public void failTest(){
        privateMethodFailTest();
    }

    @Async
    public static void staticTest() {
        log.info("\nstatic method async test: Could been executed but is sync way." +
                "\nCheck the [xxxx-xxxxx-exec-x] which is same one as previous one." +
                "\nAs The method needs to be public and non-static so that it can be proxied");
    }

    @Async
    private void privateMethodFailTest(){
        log.info("\nPrivate method async test: Could been executed but is sync way." +
                "\nCheck the [xxxx-executor-] which is same one as previous one." +
                "\nAs The method needs to be public so that it can be proxied");
    }

    @Async
    public boolean wrongReturnTypeTest() {
        log.info("\nWrong return Type Test. shoule be void or future");
        return false;
    }


    @Async
    public void step1(){
        log.info("step 1");
    }

    @Async("asyncExecutor")
    public void step2(){
        log.info("step 2");
    }

    @Async("fixedThreadPool")
    public void step3(){
        log.info("step 3");
    }

    @Async("fixedThreadPool")
    public void stepError(){
        log.info("Error. {}", 5/0);
    }

    @Async("asyncExecutor")
    public void stepError2(){
        throw new NullPointerException();
    }


    @Async
    public Future<String> asyncInvokeReturnFuture(int i) {
        log.info("asyncInvokeReturnFuture, parementer={}", i);
        Future<String> future;
        try {
            Thread.sleep(1000 * 1);
            future = new AsyncResult<String>("success:" + i);
        } catch (InterruptedException e) {
            future = new AsyncResult<String>("error");
        }
        return future;
    }
}
