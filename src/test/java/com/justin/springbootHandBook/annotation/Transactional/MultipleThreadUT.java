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
public class MultipleThreadUT {

    @Autowired
    private OutsideService outsideService;
    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void beforeEach() {
        teamRepository.deleteAll();
    }


    @Test
    void parentThread_throw_RuntimeException_rollback_but_childThread_save_success() {
        Team team = new Team("parentThread");
        assertThrows(RuntimeException.class, () -> outsideService.parentThreadThrowException(team));
        Optional<Team> parentThreadTeam = teamRepository.findByName("parentThread");
        assertFalse(parentThreadTeam.isPresent());
        Optional<Team> childThreadTeam = teamRepository.findByName("childThread");
        assertTrue(childThreadTeam.isPresent());
    }



    @Test
    void inside_method_throw_RuntimeException_with_REQUIRED_NEW_transaction() {
        Team team = new Team("parentThread");
        assertDoesNotThrow(() -> outsideService.childThreadThrowException(team));
        Optional<Team> newTeam = teamRepository.findByName("parentThread");
        assertTrue(newTeam.isPresent());
        Optional<Team> childThreadTeam = teamRepository.findByName("childThread");
        assertFalse(childThreadTeam.isPresent());
    }


}