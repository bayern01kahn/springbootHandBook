package com.justin.springbootHandBook.annotation.Transactional;

import com.justin.springbootHandBook.annotation.Transactional.dao.TeamRepository;
import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import com.justin.springbootHandBook.annotation.Transactional.service.OutsideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE) // 创建一个基于内存的数据库环境
public class NoWorkUT {

    @Autowired
    private OutsideService outsideService;
    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void beforeEach() {
        teamRepository.deleteAll();
    }

    @Test
    void tryCatch_no_rollback() {
        Team team = new Team("NGU");
        assertDoesNotThrow(() -> outsideService.tryCatch(team));
        Optional<Team> newTeam = teamRepository.findByName("NGU");
        assertTrue(newTeam.isPresent());
    }


    @Test
    void tryCatchAndThrow_no_rollback() {
        Team team = new Team("NGU");
        assertThrows(Exception.class, () -> outsideService.tryCatchAndThrow(team));
        Optional<Team> newTeam = teamRepository.findByName("NGU");
        assertTrue(newTeam.isPresent());
    }
}
