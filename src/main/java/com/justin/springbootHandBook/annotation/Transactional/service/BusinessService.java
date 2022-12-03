package com.justin.springbootHandBook.annotation.Transactional.service;

import com.justin.springbootHandBook.annotation.Transactional.dao.TeamRepository;
import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ ={@Lazy})
public class BusinessService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TransactionService transactionService;

    @Lazy
    private final BusinessService self;

    public void rollBackNotWork() {
        Team team = new Team();
        team.setName("England");
        methodInSameClassAndWithAnnotation(team); //@Transactional is on this method.
    }

    @Transactional
    public void methodInSameClassAndWithAnnotation(Team team) {
        teamRepository.save(team);
        throw new RuntimeException();
    }

    /** ------------------------------------------------------- **/

    @Transactional   //@Transactional is on this method.
    public void rollBackWorkInSameClass() {
        Team team = new Team();
        team.setName("Portugal");
        methodInSameClassAndWithNoAnnotation(team);
    }

    public void methodInSameClassAndWithNoAnnotation(Team team) {
        teamRepository.save(team);
        throw new RuntimeException();
    }

    /** ------------------------------------------------------- **/

    public void rollBackWorkRecommended() {
        Team team = new Team();
        team.setName("England");
        transactionService.methodRecommended(team);
    }

    public void rollBackWorkInSameClassSelfInject() {
        Team team = new Team();
        team.setName("SameClass");
        self.methodInSameClassAndWithAnnotation(team);
    }


    /** ---------------------------------------------------------------------------------------------------- **/




    public void saveWithCheckedRollBackForNotWork() throws SQLException {
        Team team = new Team("Iceland");
        saveTeamWithCheckedException_MethodInSameClassAsCaller(team);
        businessLogicA();
    }

    @Transactional(rollbackFor = { SQLException.class })
    public void saveTeamWithCheckedException_MethodInSameClassAsCaller(Team team) throws SQLException {
        teamRepository.save(team);
        throw new SQLException("Throwing exception for demoing rollback");
    }


    public void saveWithCheckedRollBackForWorks() throws SQLException {
        Team team = new Team("Iceland");
        transactionService.saveTeamWithCheckedException(team);
    }



    public void saveWithNoRollBack() throws SQLException {
        Team team = new Team();
        team.setName("Argentina");
        saveTeamDeclarativeWithNoRollBack(team);
        team.setName("Brazil");
        updateTeam(team);
    }









    public Team saveTeam(Team team){
        teamRepository.save(team);
        log.info("New Saved Team Id: {}", team.getId());
        throw new DataIntegrityViolationException("Throwing exception for demoing Rollback!!!");
    }

    public Long saveTeamReturnNewID(Team team){
        return teamRepository.save(team).getId();
        //throw new DataIntegrityViolationException("Throwing exception for demoing Rollback!!!");
    }

    public Team updateTeam(Team team){
        teamRepository.save(team);
        log.info("New Updated Team Name: {}", team.getName());
        throw new DataIntegrityViolationException("Throwing exception for demoing Rollback!!!");
    }

    @Transactional(noRollbackFor = { SQLException.class })
    public void saveTeamDeclarativeWithNoRollBack(Team team) throws SQLException {
        teamRepository.save(team);
        log.info("New Saved Team : ID={}, name={}", team.getId(), team.getName());
        throw new SQLException("Throwing exception for demoing rollback");
    }




    public void businessLogicA() {
        log.info("Doing some business logic A here");
    }

    public void businessLogicB() {
        log.info("Doing some business logic B here");
    }



}
