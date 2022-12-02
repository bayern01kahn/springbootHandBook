package com.justin.springbootHandBook.annotation.Transactional.service;

import com.justin.springbootHandBook.annotation.Transactional.dao.TeamRepository;
import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public void methodRecommended(Team team) {
        teamRepository.save(team);
        throw new RuntimeException();
    }

    @Transactional(rollbackFor = { SQLException.class })
    public void saveTeamWithCheckedException(Team team) throws SQLException {
        teamRepository.save(team);
        throw new SQLException("Throwing exception for demoing rollback");
    }

}
