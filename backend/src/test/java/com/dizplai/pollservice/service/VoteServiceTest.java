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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @Mock private PollService pollService;
    @Mock private OptionService optionService;
    @Mock private VoteRepository voteRepository;
    @Mock private VoteMapper voteMapper;
    @Mock private VoteAggregator voteAggregator;
    @Captor private ArgumentCaptor<VoteEntity> voteCaptor;

    @InjectMocks
    private VoteService voteService;

    @Test
    public void getVotesByPollId_shouldReturnVotesResponse_whenPollExists() {
        Long pollId = 1L;

        PollEntity existingPoll = new PollEntity();
        List<VoteEntity> existingVotes = List.of(new VoteEntity(), new VoteEntity());
        VoteResponse voteResponse1 = new VoteResponse(8L, 9L, "Option1", LocalDateTime.now());
        VoteResponse voteResponse2 = new VoteResponse(10L, 11L, "Option2", LocalDateTime.now());
        List<VoteResponse> voteResponses = List.of(voteResponse1, voteResponse2);
        List<VoteTotal> voteTotals = List.of(new VoteTotal());

        when(pollService.getPollById(pollId)).thenReturn(Optional.of(existingPoll));
        when(voteRepository.findByPollId(pollId)).thenReturn(existingVotes);
        when(voteMapper.toVoteResponse(existingVotes.get(0))).thenReturn(voteResponse1);
        when(voteMapper.toVoteResponse(existingVotes.get(1))).thenReturn(voteResponse2);
        when(voteAggregator.getVoteTotals(voteResponses)).thenReturn(voteTotals);

        VotesResponse actual = voteService.getVotesByPollId(pollId);

        verify(pollService).getPollById(pollId);
        verify(voteRepository).findByPollId(pollId);
        verify(voteAggregator).getVoteTotals(voteResponses);
        assertThat(actual.getPollId()).isEqualTo(pollId);
        assertThat(actual.getVotes()).isEqualTo(voteResponses);
        assertThat(actual.getTotals()).isSameAs(voteTotals);
    }


    @Test
    void getVotesByPollId_shouldThrowNotFoundException_whenPollDoesNotExist() {
        Long pollId = 1L;
        when(pollService.getPollById(pollId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            voteService.getVotesByPollId(pollId);
        });

        assertThat(ex.getMessage()).isEqualTo("The poll ID '1' does not exist");
        verify(pollService).getPollById(pollId);
        verifyNoInteractions(voteRepository, voteMapper, voteAggregator);
    }

    @Test
    public void createVote_shouldCreateVote_withExpectedFieldsSet(){
        Long pollId = 1L;
        Long optionId = 9L;

        VoteRequest voteRequest = new VoteRequest(optionId);

        PollEntity existingPoll = new PollEntity();
        OptionEntity existingOption = new OptionEntity();
        when(pollService.getPollById(pollId)).thenReturn(Optional.of(existingPoll));
        when(optionService.getOptionById(optionId)).thenReturn(Optional.of(existingOption));

        voteService.createVote(pollId, voteRequest);
        verify(voteRepository).save(voteCaptor.capture());
        VoteEntity actual = voteCaptor.getValue();
        assertThat(actual.getPoll()).isSameAs(existingPoll);
        assertThat(actual.getSelectedOption()).isSameAs(existingOption);
    }

    @Test
    public void createVote_shouldReturnVote(){
        Long pollId = 1L;
        Long optionId = 9L;

        VoteRequest voteRequest = new VoteRequest(optionId);

        PollEntity existingPoll = new PollEntity();
        OptionEntity existingOption = new OptionEntity();
        VoteEntity savedVote = new VoteEntity();
        VoteResponse expectedResponse = new VoteResponse();
        when(pollService.getPollById(pollId)).thenReturn(Optional.of(existingPoll));
        when(optionService.getOptionById(optionId)).thenReturn(Optional.of(existingOption));
        when(voteRepository.save(any())).thenReturn(savedVote);
        when(voteMapper.toVoteResponse(savedVote)).thenReturn(expectedResponse);

        VoteResponse response = voteService.createVote(pollId, voteRequest);
        assertThat(response).isSameAs(expectedResponse);
    }

    @Test
    void createVote_shouldThrowNotFoundException_whenPollIdDoesNotExist() {
        Long pollId = 1L;
        Long optionId = 9L;
        VoteRequest voteRequest = new VoteRequest(optionId);

        when(pollService.getPollById(pollId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            voteService.createVote(pollId, voteRequest);
        });

        assertThat(ex.getMessage()).isEqualTo("The poll ID '1' does not exist");
        verify(pollService).getPollById(pollId);
        verifyNoInteractions(voteRepository, voteMapper);
    }

    @Test
    void createVote_shouldThrowBadRequestException_whenOptionDoesNotExist() {
        Long pollId = 1L;
        Long optionId = 9L;

        VoteRequest voteRequest = new VoteRequest(optionId);

        PollEntity poll = new PollEntity();

        when(pollService.getPollById(pollId)).thenReturn(Optional.of(poll));
        when(optionService.getOptionById(optionId)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class, () -> {
            voteService.createVote(pollId, voteRequest);
        });

        assertThat(ex.getMessage()).isEqualTo("The option ID '9' does not exist");
        verify(pollService).getPollById(pollId);
        verify(optionService).getOptionById(optionId);
        verifyNoInteractions(voteRepository, voteMapper);
    }

}
