package com.dizplai.pollservice.controller;

import com.dizplai.pollservice.model.VoteRequest;
import com.dizplai.pollservice.model.VoteResponse;
import com.dizplai.pollservice.model.VotesResponse;
import com.dizplai.pollservice.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @GetMapping("/poll/{pollId}/votes")
    public ResponseEntity<VotesResponse> getVotes(@PathVariable("pollId") Long pollId) {
        log.info("GetVotes request received for pollId: {}", pollId);
        VotesResponse votes = voteService.getVotesByPollId(pollId);
        log.info("GetVotes request successfully completed for pollId: {} Response: {}", pollId, votes);
        return ResponseEntity.ok(votes);
    }


    @PostMapping("/poll/{pollId}/vote")
    public ResponseEntity<VoteResponse> createVote(@PathVariable("pollId") Long pollId,
                                                   @RequestBody @Valid VoteRequest voteRequest) {
        VoteResponse voteResponse = voteService.createVote(pollId, voteRequest);
        return new ResponseEntity<>(voteResponse, HttpStatus.CREATED);
    }


}
