package com.justin.springbootHandBook.annotation.Transactional.controller;//package com.sap.ns.controller;


import com.justin.springbootHandBook.annotation.Async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class TransactionalController {

    @Autowired
    public AsyncService asyncService;

    @GetMapping("/transactional/work")
    public HttpStatus work() {

        log.info("in Controller");

        return HttpStatus.OK;
    }

    @GetMapping("/transactional/workWithFutureReturn")
    public HttpStatus workWithFutureReturn() {

        log.info("in workWithFutureReturn");

        return HttpStatus.OK;
    }


}
