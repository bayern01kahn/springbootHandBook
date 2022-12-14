package com.justin.springbootHandBook.annotation.Transactional.service;

import com.justin.springbootHandBook.annotation.Transactional.dao.TeamRepository;
import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InsideService {

    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public void doSth(Team team){
        teamRepository.save(team);
        log.info("do sth");
    }

    @Transactional
    public void save_ThrowException(Team team){
        teamRepository.save(team);
        throw new RuntimeException("Child Thread throw Exception");
    }


    @Transactional
    public void REQUIRED_TransactionalMethodAndThrowNullPointerException(){
        throw new NullPointerException("sth is null");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void REQUIRES_NEW_TransactionalMethodAndThrowRuntimeException(){
        throw new NullPointerException("sth is null");
    }

    @Transactional(noRollbackFor = NullPointerException.class)
    public void noRollbackFor_TransactionalMethodAndThrowRuntimeException(){
        throw new NullPointerException("sth is null");
    }


}
