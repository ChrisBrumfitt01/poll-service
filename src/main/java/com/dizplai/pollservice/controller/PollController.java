package com.dizplai.pollservice.controller;

import com.dizplai.pollservice.model.PollRequest;
import com.dizplai.pollservice.model.PollResponse;
import com.dizplai.pollservice.service.PollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/active")
    public ResponseEntity<List<PollResponse>> getActivePolls() {
        log.info("GetActivePolls request received");
        List<PollResponse> activePolls = pollService.getActivePolls();
        log.info("GetActivePolls request completed successfully. Response: {}", activePolls);
        return ResponseEntity.ok(activePolls);
    }


}
