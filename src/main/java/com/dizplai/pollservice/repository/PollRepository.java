package com.dizplai.pollservice.repository;

import com.dizplai.pollservice.entity.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<PollEntity, Long> {
    @Query("SELECT p FROM poll p WHERE p.activeFrom <= :now AND p.activeTo >= :now")
    List<PollEntity> findActivePolls(@Param("now") LocalDateTime now);
}
