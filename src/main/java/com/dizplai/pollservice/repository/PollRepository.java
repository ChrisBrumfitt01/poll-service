package com.dizplai.pollservice.repository;

import com.dizplai.pollservice.entity.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<PollEntity, Long> {
}
