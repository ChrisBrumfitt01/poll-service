package com.dizplai.pollservice.repository;

import com.dizplai.pollservice.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    public List<VoteEntity> findByPollId(Long pollId);
}
