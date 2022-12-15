package com.justin.springbootHandBook.annotation.Transactional.service;

import com.justin.springbootHandBook.annotation.Transactional.dao.TeamRepository;
import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import com.justin.springbootHandBook.exception.MyCheckedException;
import com.justin.springbootHandBook.exception.MyRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class OutsideService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private InsideService insideService;

    @Transactional
    public Team dbOperateAndCallTransactionalMethod(Team team){
        teamRepository.save(team);
        try{
            insideService.REQUIRED_TransactionalMethodAndThrowNullPointerException();
        } catch (NullPointerException e){
            log.error("Exception Been catch without throws in current method transaction flow: {}", e.getMessage());
        }
        log.info("New Saved Team Id: {}", team.getId());
        return team;
    }


    //without 'rollbackFor'
//    @Transactional
//    public void changeNum() throws ClassNotFoundException {
//        svcA.save();    // svcA.save() will execute normally.
//        Class.forName("The Class do not Exist");  //throws
//        testBMapper.descNumB();
//    }


    //with 'rollbackFor'
//    @Transactional(rollbackFor = {ClassNotFoundException.class})
//    public void dbOperate() throws ClassNotFoundException {
//        svcA.save();    // svcA.save() will rollback
//        Class.forName("The Class do not Exist"); //throws
//        svcB.update();
//    }

//    @Transactional(noRollbackFor = {ArithmeticException.class})
//    public void dbOperate(){
//        svcA.save();
//        int a = 1/0;  //
//        svcB.update();
//    }

//    @Transactional(noRollbackFor = {ArithmeticException.class})
//    public void dbOperate(){
//        svcA.save();    // svcA.save() will execute normally.
//        try {
//            int a = 1/0;
//        }
//        catch (Exception e){}
//        svcB.update();  // svcB.update() will execute normally.
//    }


    @Transactional(rollbackFor = {ClassNotFoundException.class})
    public void changeNum()  {
        svcA.save();    // svcA.save() will execute normally.
        try {
            Class.forName("The Class do not Exist"); //throws
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        svcB.update();  // svcB.update() will execute normally.
    }


    @Transactional
    public Team dbOperateAndCallTransactionalMethod_REQUIRES_NEW(Team team){
        teamRepository.save(team);
        try{
            insideService.REQUIRES_NEW_TransactionalMethodAndThrowRuntimeException();
        } catch (NullPointerException e){
            log.error("Exception Been catch without throws in current method transaction flow: {}", e.getMessage());
        }
        log.info("New Saved Team Id: {}", team.getId());
        return team;
    }

    @Transactional
    public Team dbOperateAndCallTransactionalMethod_noRollbackFor(Team team){
        teamRepository.save(team);
        try{
            insideService.noRollbackFor_TransactionalMethodAndThrowRuntimeException();
        } catch (NullPointerException e){
            log.error("Exception Been catch without throws in current method transaction flow: {}", e.getMessage());
        }
        log.info("New Saved Team Id: {}", team.getId());
        return team;
    }



    @Transactional
    public void parentThreadThrowException(Team team){
        teamRepository.save(team);
        new Thread(() -> insideService.doSth(new Team("childThread"))).start();
        throw new RuntimeException("Parent Thread throw Exception");
    }

    @Transactional
    public void childThreadThrowException(Team team){
        teamRepository.save(team);
        new Thread(() -> insideService.save_ThrowException(new Team("childThread"))).start();
    }


    @Transactional
    public void tryCatch(Team team){
        try {
            teamRepository.save(team);
            int a = 1/0;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Transactional
    public void tryCatchCustomizeException(Team team){
        try {
            teamRepository.save(team);
            throw new MyCheckedException("my custom exception");
        } catch (MyCheckedException e) {
            log.error(e.getMessage());
        }
    }

    @Transactional
    public void tryCatchAndThrow(Team team) throws Exception{
        try {
            teamRepository.save(team);
            int a = 1/0;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception();
        }
    }


    @Transactional
    public void tryCatchAndThrowOtherRuntimeException(Team team) {
        try {
            teamRepository.save(team);
            int a = 1/0;
        } catch (ArithmeticException e) {
            log.error(e.getMessage());
            throw new NullPointerException();
        }
    }

    @Transactional
    public void tryCatchAndThrowOtherCheckedException(Team team) throws TimeoutException {
        try {
            teamRepository.save(team);
            int a = 1/0;
        } catch (ArithmeticException e) {
            log.error(e.getMessage());
            throw new TimeoutException();
        }
    }

    @Transactional
    public void tryCatchAndThrowCustomizeRuntimeException(Team team) {
        try {
            teamRepository.save(team);
            int a = 1/0;
        } catch (ArithmeticException e) {
            log.error(e.getMessage());
            throw new MyRuntimeException();
        }
    }

    @Transactional
    public void tryCatchAndThrowCustomizeCheckedException(Team team) throws MyCheckedException {
        try {
            teamRepository.save(team);
            throw new MyCheckedException("my custom exception");
        } catch (MyCheckedException e) {
            log.error(e.getMessage());
            throw new MyCheckedException("MyException");
        }
    }


    @Transactional
    public void tryCatchAndThrowCustomizeCheckedExceptionSetRollbackOnly(Team team) throws MyCheckedException {
        try {
            teamRepository.save(team);
            throw new MyCheckedException("my custom exception");
        } catch (MyCheckedException e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new MyCheckedException("MyException");
        }
    }
}
