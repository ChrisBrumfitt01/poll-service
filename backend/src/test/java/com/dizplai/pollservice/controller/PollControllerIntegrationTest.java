package com.dizplai.pollservice.controller;

import com.dizplai.pollservice.entity.OptionEntity;
import com.dizplai.pollservice.entity.PollEntity;
import com.dizplai.pollservice.model.PollRequest;
import com.dizplai.pollservice.repository.OptionRepository;
import com.dizplai.pollservice.repository.PollRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PollControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private OptionRepository optionRepository;

    String question = "Who is the best team in England?";
    List<String> options = List.of("Liverpool", "Manchester City", "Arsenal");

    @AfterEach
    void tearDown() {
        pollRepository.deleteAll();
    }

    @Test
    void createPoll_shouldReturn201_AndAddPollToDatabase() throws Exception {
        PollRequest pollRequest = PollRequest.builder()
                .question(question)
                .options(options)
                .activeFrom(LocalDateTime.now().minusDays(1))
                .activeTo(LocalDateTime.now().plusDays(1))
                .build();

        mockMvc.perform(post("/poll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pollRequest)))
                .andExpect(status().isCreated());

        List<PollEntity> polls = pollRepository.findAll();
        assertThat(polls.size()).isEqualTo(1);

        PollEntity poll = polls.get(0);
        assertThat(poll.getQuestion()).isEqualTo(question);

        List<OptionEntity> optionEntities = optionRepository.findByPollId(poll.getId());
        assertThat(optionEntities.stream().map(OptionEntity::getDescription)).containsExactlyInAnyOrderElementsOf(options);
    }


    @Test
    public void getActivePolls_shouldReturn200_AndActivePollsOnly() throws Exception {
        setupData();
        mockMvc.perform(get("/poll/active"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].question").value(question))
                .andExpect(jsonPath("$[0].options[0].description").value(options.get(0)))
                .andExpect(jsonPath("$[0].options[1].description").value(options.get(1)))
                .andExpect(jsonPath("$[0].options[2].description").value(options.get(2)));
    }


    private void setupData(){
        PollEntity activePoll = new PollEntity();
        activePoll.setQuestion(question);
        activePoll.setActiveFrom(LocalDateTime.now().minusDays(1));
        activePoll.setActiveTo(LocalDateTime.now().plusDays(1));
        List<OptionEntity> optionEntities = options.stream()
                .map(o -> new OptionEntity(null, activePoll, o))
                .toList();
        activePoll.setOptions(optionEntities);

        PollEntity inactivePoll = new PollEntity();
        inactivePoll.setQuestion("Inactive");
        inactivePoll.setActiveFrom(LocalDateTime.now().minusDays(2));
        inactivePoll.setActiveTo(LocalDateTime.now().minusDays(1));
        List<OptionEntity> inactivePollOptions = options.stream()
                .map(o -> new OptionEntity(null, inactivePoll, o))
                .toList();
        inactivePoll.setOptions(inactivePollOptions);

        pollRepository.saveAll(List.of(activePoll, inactivePoll));
    }

}
