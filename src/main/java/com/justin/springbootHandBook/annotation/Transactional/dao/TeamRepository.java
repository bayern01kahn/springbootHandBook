package com.justin.springbootHandBook.annotation.Transactional.dao;

import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("select t from Team t where t.name = ?1")
    Optional<Team> findByName(String name);

}
