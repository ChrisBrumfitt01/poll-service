package com.dizplai.pollservice.service;

import com.dizplai.pollservice.entity.VoteEntity;
import com.dizplai.pollservice.exception.NotFoundException;
import com.dizplai.pollservice.model.VotesResponse;
import com.dizplai.pollservice.repository.VoteRepository;
import com.dizplai.pollservice.service.mapper.VoteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final PollService pollService;
    private final VoteRepository voteRepository;
    private final VoteMapper mapper;

    public VotesResponse getVotesByPollId(Long pollId) {
        pollService.getPollById(pollId).orElseThrow(
                () -> new NotFoundException(String.format("The poll ID '%d' does not exist", pollId)
        ));
        List<VoteEntity> votes = voteRepository.findByPollId(pollId);
        return mapper.toVotesResponse(pollId, votes);
    }

}
