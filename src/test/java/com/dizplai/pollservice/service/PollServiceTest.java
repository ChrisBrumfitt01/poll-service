package com.dizplai.pollservice.service;

import com.dizplai.pollservice.entity.OptionEntity;
import com.dizplai.pollservice.entity.PollEntity;
import com.dizplai.pollservice.exception.BadRequestException;
import com.dizplai.pollservice.model.PollRequest;
import com.dizplai.pollservice.model.PollResponse;
import com.dizplai.pollservice.repository.PollRepository;
import com.dizplai.pollservice.service.mapper.PollMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PollServiceTest {

    private static final String QUESTION = "This is a test question?";
    private static final List<String> OPTIONS = List.of("Option1", "Option2", "Option3");
    private static final LocalDateTime ACTIVE_FROM = LocalDateTime.now();
    private static final LocalDateTime ACTIVE_TO = LocalDateTime.now().plusDays(1L);

    @Mock private PollRepository pollRepository;
    @Mock private PollMapper pollMapper;
    @Captor private ArgumentCaptor<PollEntity> captor;

    @InjectMocks
    private PollService pollService;


    @Test
    public void createPoll_shouldSavePollEntityWithExpectedFieldsSet() {
        PollRequest pollRequest = new PollRequest(
                QUESTION,
                OPTIONS,
                ACTIVE_FROM,
                ACTIVE_TO
        );

        PollEntity toSave = new PollEntity();
        PollEntity savedPoll = new PollEntity();
        PollResponse expectedResponse = new PollResponse();
        when(pollMapper.toPollEntity(pollRequest)).thenReturn(toSave);
        when(pollRepository.save(toSave)).thenReturn(savedPoll);
        when(pollMapper.toPollResponse(savedPoll)).thenReturn(expectedResponse);

        PollResponse actualResponse = pollService.createPoll(pollRequest);

        verify(pollRepository).save(toSave);
        verify(pollMapper).toPollResponse(savedPoll);
        assertThat(actualResponse).isSameAs(expectedResponse);
    }

    @Test
    void createPoll_shouldThrowBadRequestException_whenActiveFromAfterActiveTo() {
        PollRequest pollRequest = new PollRequest(
                QUESTION,
                OPTIONS,
                ACTIVE_FROM,
                ACTIVE_TO.minusDays(2)
        );

        BadRequestException ex = assertThrows(BadRequestException.class, () -> {
            pollService.createPoll(pollRequest);
        });
        assertThat(ex.getMessage()).isEqualTo("'activeTo' must come after 'activeFrom'");
        verifyNoInteractions(pollRepository, pollMapper);
    }

}
