package com.justin.springbootHandBook.annotation.Transactional.service;

import com.justin.springbootHandBook.annotation.Transactional.dao.TeamRepository;
import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public void methodA(Team team){
        teamRepository.save(team);
        this.methodB_inSameClass(team);
    }

    @Transactional
    private void methodB_inSameClass(Team team) {
        teamRepository.updateTeam(team.getName(), team.getId());
    }
}
