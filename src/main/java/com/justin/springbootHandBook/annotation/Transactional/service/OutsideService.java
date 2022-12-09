package com.justin.springbootHandBook.annotation.Transactional.service;

import com.justin.springbootHandBook.annotation.Transactional.dao.TeamRepository;
import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
