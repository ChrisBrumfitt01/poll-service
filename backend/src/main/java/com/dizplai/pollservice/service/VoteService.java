package com.dizplai.pollservice.service;

import com.dizplai.pollservice.entity.OptionEntity;
import com.dizplai.pollservice.entity.PollEntity;
import com.dizplai.pollservice.entity.VoteEntity;
import com.dizplai.pollservice.exception.BadRequestException;
import com.dizplai.pollservice.exception.NotFoundException;
import com.dizplai.pollservice.model.VoteRequest;
import com.dizplai.pollservice.model.VoteResponse;
import com.dizplai.pollservice.model.VoteTotal;
import com.dizplai.pollservice.model.VotesResponse;
import com.dizplai.pollservice.repository.VoteRepository;
import com.dizplai.pollservice.service.aggregator.VoteAggregator;
import com.dizplai.pollservice.service.mapper.VoteMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final PollService pollService;
    private final OptionService optionService;
    private final VoteRepository voteRepository;
    private final VoteMapper mapper;
    private final VoteAggregator voteAggregator;

    public VotesResponse getVotesByPollId(Long pollId) {
        pollService.getPollById(pollId).orElseThrow(
                () -> new NotFoundException(String.format("The poll ID '%d' does not exist", pollId)
        ));

        List<VoteResponse> votes = voteRepository.findByPollId(pollId)
                .stream()
                .map(mapper::toVoteResponse)
                .collect(Collectors.toList());

        List<VoteTotal> voteTotals = voteAggregator.getVoteTotals(votes);
        return new VotesResponse(pollId, votes, voteTotals);
    }

    @Transactional
    public VoteResponse createVote(Long pollId, VoteRequest vote){
        PollEntity poll = pollService.getPollById(pollId)
                .orElseThrow(() -> new NotFoundException(String.format("The poll ID '%d' does not exist", pollId)));
        OptionEntity option = optionService.getOptionById(vote.getOptionId())
                .orElseThrow(() -> new BadRequestException(String.format("The option ID '%d' does not exist", vote.getOptionId())));

        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setPoll(poll);
        voteEntity.setSelectedOption(option);

        VoteEntity savedVote = voteRepository.save(voteEntity);
        return mapper.toVoteResponse(savedVote);
    }

}
