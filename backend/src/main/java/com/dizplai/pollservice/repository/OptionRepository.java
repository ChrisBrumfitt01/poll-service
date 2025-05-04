package com.dizplai.pollservice.repository;

import com.dizplai.pollservice.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OptionRepository extends JpaRepository<OptionEntity, Long> {
    public List<OptionEntity> findByPollId(Long pollId);
}
