package com.justin.springbootHandBook.annotation.Transactional;

import com.justin.springbootHandBook.annotation.Transactional.dao.TeamRepository;
import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import com.justin.springbootHandBook.annotation.Transactional.service.OutsideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE) // 创建一个基于内存的数据库环境
public class RollbackWorkUT {

    @Autowired
    private OutsideService outsideService;
    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void beforeEach() {
        teamRepository.deleteAll();
    }


    @Test
    void tryCatchAndThrowOtherRuntimeException_will_rollback() {
        Team team = new Team("NGU");
        assertThrows(Exception.class, () -> outsideService.tryCatchAndThrowOtherRuntimeException(team));
        Optional<Team> newTeam = teamRepository.findByName("NGU");
        assertFalse(newTeam.isPresent());
    }

    @Test
    void tryCatchAndThrowCustomizeRuntimeException_will_rollback() {
        Team team = new Team("NGU");
        assertThrows(Exception.class, () -> outsideService.tryCatchAndThrowCustomizeRuntimeException(team));
        Optional<Team> newTeam = teamRepository.findByName("NGU");
        assertFalse(newTeam.isPresent());
    }

    @Test
    void tryCatchAndThrowCustomizeCheckedException_setRollbackOnly_will_rollback() {
        Team team = new Team("NGU");
        assertThrows(Exception.class, () -> outsideService.tryCatchAndThrowCustomizeCheckedExceptionSetRollbackOnly(team));
        Optional<Team> newTeam = teamRepository.findByName("NGU");
        assertFalse(newTeam.isPresent());
    }

}
