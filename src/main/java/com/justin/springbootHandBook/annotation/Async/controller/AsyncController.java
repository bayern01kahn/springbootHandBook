package com.justin.springbootHandBook.annotation.Async.controller;//package com.sap.ns.controller;


import com.justin.springbootHandBook.annotation.Async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class AsyncController {

    @Autowired
    public AsyncService asyncService;

    @GetMapping("/async/work")
    public HttpStatus work() {

        log.info("in Controller");
        asyncService.step1();
        asyncService.step2();
        asyncService.step3();
        return HttpStatus.OK;
    }

    @GetMapping("/async/workWithFutureReturn")
    public HttpStatus workWithFutureReturn() {

        log.info("in workWithFutureReturn");
        Future<String> stringFuture = asyncService.asyncInvokeReturnFuture(5);

        while (true) {
            if (stringFuture.isDone() && !stringFuture.isCancelled()) {
                try {
                    log.info("Get Future Value {}", stringFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return HttpStatus.OK;
    }

    @GetMapping("/async/work/methodsInSameClass")
    public HttpStatus work1() {

        log.info("test async working method in same class");
        asyncService.allInSameClass();
        return HttpStatus.OK;
    }

    @GetMapping("/async/fail/methodsInSameClass")
    public HttpStatus fail1() {

        log.info("test async not working");
        asyncService.all();
        return HttpStatus.OK;
    }

    @GetMapping("/async/fail/privateMethod")
    public HttpStatus fail2() {

        log.info("test async not working");
        asyncService.failTest();
        return HttpStatus.OK;
    }

    @GetMapping("/async/fail/staticMethod")
    public HttpStatus fail4() {

        log.info("test async not working");
        asyncService.staticTest();
        return HttpStatus.OK;
    }

    @GetMapping("/async/fail/returnShouldBeVoidOrFuture")
    public HttpStatus fail3() {

        log.info("test async not working");
        asyncService.wrongReturnTypeTest();
        return HttpStatus.OK;
    }


    @GetMapping("/async/exception")
    public HttpStatus exception() {

        log.info("test async exception");
        asyncService.stepError();
        asyncService.stepError2();
        return HttpStatus.OK;
    }
}
