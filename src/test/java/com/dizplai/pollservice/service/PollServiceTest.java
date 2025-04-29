package com.dizplai.pollservice.service;

import com.dizplai.pollservice.entity.OptionEntity;
import com.dizplai.pollservice.entity.PollEntity;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PollServiceTest {

    private static final String QUESTION = "This is a test question?";
    private static final List<String> OPTIONS = List.of("Option1", "Option2", "Option3");

    @Mock private PollRepository pollRepository;
    @Mock private PollMapper pollMapper;
    @Captor private ArgumentCaptor<PollEntity> captor;

    @InjectMocks
    private PollService pollService;

    @Test
    public void createPoll_shouldSavePollEntityWithExpectedFieldsSet() {
        PollRequest request = buildRequest();

        pollService.createPoll(request);

        verify(pollRepository).save(captor.capture());
        PollEntity savedEntity = captor.getValue();
        assertThat(savedEntity.getQuestion()).isEqualTo(QUESTION);
        assertThat(savedEntity.getOptions().stream().map(OptionEntity::getDescription).toList()).isEqualTo(OPTIONS);
    }


    @Test
    public void createPoll_shouldReturnSavedPoll() {
        PollRequest request = buildRequest();

        PollEntity entity = new PollEntity(1L, QUESTION, List.of(), LocalDateTime.now());
        PollResponse response = new PollResponse(1L, QUESTION, List.of(), LocalDateTime.now());

        when(pollRepository.save(any())).thenReturn(entity);
        when(pollMapper.toPollResponse(entity)).thenReturn(response);

        PollResponse returned = pollService.createPoll(request);
        assertThat(returned).isSameAs(response);
    }

    private PollRequest buildRequest() {
        PollRequest request = new PollRequest();
        request.setQuestion(QUESTION);
        request.setOptions(OPTIONS);
        return request;
    }

}
