package com.justin.springbootHandBook.annotation.Transactional.controller;

import com.justin.springbootHandBook.annotation.Transactional.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping(value = "/v1/transactional")
public class TransactionalFailController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/rollBackNotWork")
    public HttpStatus rollBackNotWork() throws SQLException {
        businessService.rollBackNotWork();
        return HttpStatus.OK;
    }

    /** -------------------------------------------------------------------------------------- **/

    @GetMapping("/saveWithCheckedRollBackForNotWork")
    public HttpStatus saveWithCheckedRollback() throws SQLException {
        businessService.saveWithCheckedRollBackForNotWork();
        return HttpStatus.OK;
    }


//
//    @GetMapping("/saveWithNoRollback")
//    public HttpStatus saveWithNoRollback() throws SQLException {
//        businessService.saveWithNoRollBack();
//        return HttpStatus.OK;
//    }

}
