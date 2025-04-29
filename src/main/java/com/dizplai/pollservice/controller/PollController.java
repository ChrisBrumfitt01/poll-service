package com.dizplai.pollservice.controller;

import com.dizplai.pollservice.model.PollRequest;
import com.dizplai.pollservice.model.PollResponse;
import com.dizplai.pollservice.service.PollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poll")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;
    @PostMapping(produces = "application/json")
    public ResponseEntity<PollResponse> createPoll(@RequestBody @Valid PollRequest pollRequest) {
        PollResponse createdPoll = pollService.createPoll(pollRequest);
        return new ResponseEntity<>(createdPoll, HttpStatus.CREATED);
    }

}
