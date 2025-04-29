package com.dizplai.pollservice.service;

import com.dizplai.pollservice.entity.OptionEntity;
import com.dizplai.pollservice.entity.PollEntity;
import com.dizplai.pollservice.model.PollRequest;
import com.dizplai.pollservice.model.PollResponse;
import com.dizplai.pollservice.repository.PollRepository;
import com.dizplai.pollservice.service.mapper.PollMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PollService {

    private final PollRepository pollRepository;
    private final PollMapper mapper;

    public PollResponse createPoll(PollRequest poll) {
        PollEntity pollEntity = new PollEntity();
        pollEntity.setQuestion(poll.getQuestion());

        poll.getOptions().forEach(optionDescription -> {
            OptionEntity optionEntity = new OptionEntity();
            optionEntity.setDescription(optionDescription);
            pollEntity.addOption(optionEntity);
        });

        PollEntity savedPoll = pollRepository.save(pollEntity);
        return mapper.toPollResponse(savedPoll);
    }


}
