package com.dizplai.pollservice.repository;

import com.dizplai.pollservice.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {
}
