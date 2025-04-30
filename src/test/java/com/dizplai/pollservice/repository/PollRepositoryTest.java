package com.dizplai.pollservice.repository;

import com.dizplai.pollservice.entity.PollEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class PollRepositoryTest {

    @Autowired
    private PollRepository pollRepository;

    @BeforeEach
    public void setup(){
        LocalDateTime now = LocalDateTime.now();

        PollEntity activePoll1 = new PollEntity();
        activePoll1.setQuestion("Active poll 1");
        activePoll1.setActiveFrom(now.minusHours(1));
        activePoll1.setActiveTo(now.plusHours(1));
        pollRepository.save(activePoll1);

        PollEntity activePoll2 = new PollEntity();
        activePoll2.setQuestion("Active poll 2");
        activePoll2.setActiveFrom(now.minusDays(1));
        activePoll2.setActiveTo(now.plusDays(1));
        pollRepository.save(activePoll2);

        PollEntity futurePoll = new PollEntity();
        futurePoll.setQuestion("Future poll");
        futurePoll.setActiveFrom(now.plusHours(1));
        futurePoll.setActiveTo(now.plusHours(2));
        pollRepository.save(futurePoll);

        PollEntity pastPoll = new PollEntity();
        pastPoll.setQuestion("Past poll");
        pastPoll.setActiveFrom(now.minusHours(2));
        pastPoll.setActiveTo(now.minusHours(1));
        pollRepository.save(pastPoll);
    }

    @Test
    void findActivePolls_shouldReturnPollsWhereNowIsBetweenActiveFromAndActiveTo() {
        List<PollEntity> result = pollRepository.findActivePolls(LocalDateTime.now());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().map(PollEntity::getQuestion).collect(Collectors.toList()))
                .containsExactlyInAnyOrder("Active poll 1", "Active poll 2");

    }


    @Test
    void findActivePolls_shouldReturnPollsWhereNowIsBetweenActiveFromAndActiveTo2() {
        List<PollEntity> result = pollRepository.findActivePolls(LocalDateTime.now());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().map(PollEntity::getQuestion).collect(Collectors.toList()))
                .containsExactlyInAnyOrder("Active poll 1", "Active poll 2");

    }


}
