package com.dizplai.pollservice.controller;

import com.dizplai.pollservice.model.PollRequest;
import com.dizplai.pollservice.model.PollResponse;
import com.dizplai.pollservice.service.PollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poll")
@RequiredArgsConstructor
@Slf4j
public class PollController {

    private final PollService pollService;
    @PostMapping
    public ResponseEntity<PollResponse> createPoll(@RequestBody @Valid PollRequest pollRequest) {
        log.info("CreatePoll request received: {}", pollRequest);
        PollResponse createdPoll = pollService.createPoll(pollRequest);
        log.info("CreatePoll request completed successfully. Request: {}, Response: {}", pollRequest, createdPoll);
        return new ResponseEntity<>(createdPoll, HttpStatus.CREATED);
    }



}
