package com.justin.springbootHandBook.annotation.Transactional.controller;//package com.sap.ns.controller;

import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import com.justin.springbootHandBook.annotation.Transactional.service.TransactionService;
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
    private TransactionService transactionService;

    @GetMapping("/transactional/save")
    public HttpStatus save() {
        Team team = new Team();
        team.setName("Germany");
        log.info("New Team ID: {}", transactionService.saveTeamReturnNewID(team));
        return HttpStatus.OK;
    }


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
