package com.justin.springbootHandBook.annotation.Transactional.service;

import com.justin.springbootHandBook.annotation.Transactional.dao.TeamRepository;
import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public Long saveTeamReturnNewID(Team team){
        teamRepository.save(team);
        throw new DataIntegrityViolationException("Throwing exception for demoing Rollback!!!");
    }

}
