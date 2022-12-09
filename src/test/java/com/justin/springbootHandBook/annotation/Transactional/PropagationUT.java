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
//@DataJpaTest    // 只启动JPA组件不启动全环境,  可以在springboot的环境只测JPA组件，它会默认扫描@Entity和@Repository注解
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE) // 创建一个基于内存的数据库环境
public class PropagationUT {

    @Autowired
    private OutsideService outsideService;
    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void beforeEach() {
        teamRepository.deleteAll();
    }

    /**
     * The default @Transactional propagation is REQUIRED
     * If the RuntimeException throws out of the transactional proxy,
     * Spring marks the current transaction as rollback only.
     *
     * insideService.method() will throw NullPointerException which is RuntimeException.
     * so Spring will mark current transaction as rollback only. and throw 'UnexpectedRollbackException'
     */
    @Test
    void inside_method_throw_RuntimeException_with_default_REQUIRED_transaction() {
        Team team = new Team("NGU");
        assertThrows(UnexpectedRollbackException.class, () -> outsideService.dbOperateAndCallTransactionalMethod(team),
                "Transaction silently rolled back because it has been marked as rollback-only");
        Optional<Team> newTeam = teamRepository.findByName("NGU");
        assertFalse(newTeam.isPresent());
    }


    /**
     * scenario 1:
     * we just want catch some RuntimeException happened in inside transaction
     * and do not trigger the outside transaction rollback.
     *
     * way 1: use @Transactional(propagation = Propagation.REQUIRES_NEW)
     *
     */
    @Test
    void inside_method_throw_RuntimeException_with_REQUIRED_NEW_transaction() {
        Team team = new Team("NGU");
        assertDoesNotThrow(() -> outsideService.dbOperateAndCallTransactionalMethod_REQUIRES_NEW(team));
        Optional<Team> newTeam = teamRepository.findByName("NGU");
        assertTrue(newTeam.isPresent());
    }

    /**
     * scenario 1:
     * way 2:  use @Transactional(noRollbackFor = NullPointerException.class)
     */
    @Test
    void inside_method_throw_RuntimeException_with_REQUIRED_noRollbackFor_transaction() {
        Team team = new Team("NGU");
        assertDoesNotThrow(() -> outsideService.dbOperateAndCallTransactionalMethod_noRollbackFor(team));
        Optional<Team> newTeam = teamRepository.findByName("NGU");
        assertTrue(newTeam.isPresent());
    }

}