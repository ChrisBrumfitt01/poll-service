package com.dizplai.pollservice.service;

import com.dizplai.pollservice.entity.PollEntity;
import com.dizplai.pollservice.entity.VoteEntity;
import com.dizplai.pollservice.exception.NotFoundException;
import com.dizplai.pollservice.model.VotesResponse;
import com.dizplai.pollservice.repository.VoteRepository;
import com.dizplai.pollservice.service.mapper.VoteMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @Mock private PollService pollService;

    @Mock private VoteRepository voteRepository;

    @Mock private VoteMapper voteMapper;

    @InjectMocks
    private VoteService voteService;

    @Test
    void getVotesByPollId_shouldReturnVotesResponse_whenPollExists() {
        Long pollId = 1L;

        PollEntity existingPoll = new PollEntity();
        List<VoteEntity> existingVotes = List.of(new VoteEntity(), new VoteEntity());
        VotesResponse mockResponse = new VotesResponse();

        when(pollService.getPollById(pollId)).thenReturn(Optional.of(existingPoll));
        when(voteRepository.findByPollId(pollId)).thenReturn(existingVotes);
        when(voteMapper.toVotesResponse(pollId, existingVotes)).thenReturn(mockResponse);

        VotesResponse result = voteService.getVotesByPollId(pollId);

        assertThat(result).isSameAs(mockResponse);
        verify(pollService).getPollById(pollId);
        verify(voteRepository).findByPollId(pollId);
        verify(voteMapper).toVotesResponse(pollId, existingVotes);
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
        verifyNoInteractions(voteRepository, voteMapper);
    }


}
