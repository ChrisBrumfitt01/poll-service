package com.dizplai.pollservice.service;

import com.dizplai.pollservice.entity.OptionEntity;
import com.dizplai.pollservice.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    public Optional<OptionEntity> getOptionById(Long id){
        return optionRepository.findById(id);
    }


}
