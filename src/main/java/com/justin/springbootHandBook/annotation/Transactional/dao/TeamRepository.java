package com.justin.springbootHandBook.annotation.Transactional.dao;

import com.justin.springbootHandBook.annotation.Transactional.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
