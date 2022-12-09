package com.justin.springbootHandBook.annotation.Transactional.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InsideService {


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
