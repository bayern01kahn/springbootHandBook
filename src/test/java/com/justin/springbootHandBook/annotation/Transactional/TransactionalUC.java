package com.justin.springbootHandBook.annotation.Transactional;

import com.justin.springbootHandBook.annotation.Transactional.dao.TeamRepository;
import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import com.justin.springbootHandBook.annotation.Transactional.service.BusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DataJpaTest    // 只启动JPA组件不启动全环境,  可以在springboot的环境只测JPA组件，它会默认扫描@Entity和@Repository注解
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE) // 创建一个基于内存的数据库环境
public class TransactionalUC {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void beforeEach() {
        teamRepository.deleteAll();
    }

    /**
     * If the RuntimeException throws out of the transactional proxy,
     * Spring marks the current transaction as rollback only.
     *
     * Here recordService.record() will throws IllegalArgumentException which is RuntimeException.
     * so Spring will marks current transaction as rollback only.
     */
    @Test
    void default_Propagation_REQUIRED_() {
        Team team = new Team("NGU");
        assertThrows(UnexpectedRollbackException.class, () -> businessService.saveTeamAndRecord(team), "Transaction silently rolled back because it has been marked as rollback-only");
        Optional<Team> newTeam = teamRepository.findByName("NGU");
        assertFalse(newTeam.isPresent());
    }

}