package com.dizplai.pollservice.service;

import com.dizplai.pollservice.entity.PollEntity;
import com.dizplai.pollservice.exception.BadRequestException;
import com.dizplai.pollservice.model.PollRequest;
import com.dizplai.pollservice.model.PollResponse;
import com.dizplai.pollservice.repository.PollRepository;
import com.dizplai.pollservice.service.mapper.PollMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PollService {

    private final PollRepository pollRepository;
    private final PollMapper mapper;
    @Transactional
    public PollResponse createPoll(PollRequest poll) {
        validateCreatePollRequest(poll);
        PollEntity pollEntity = mapper.toPollEntity(poll);
        log.info("Saving new poll to database: {}", pollEntity);
        PollEntity savedPoll = pollRepository.save(pollEntity);
        log.info("Successfully saved new poll to database: {}", pollEntity);
        return mapper.toPollResponse(savedPoll);
    }

    public List<PollResponse> getActivePolls() {
        return pollRepository.findActivePolls(LocalDateTime.now())
                .stream()
                .map(mapper::toPollResponse)
                .collect(Collectors.toList());
    }

    public Optional<PollEntity> getPollById(Long id){
        return pollRepository.findById(id);
    }

    private void validateCreatePollRequest(PollRequest poll){
        if(poll.getActiveFrom().isAfter(poll.getActiveTo())){
            throw new BadRequestException("'activeTo' must come after 'activeFrom'");
        }
    }

}
