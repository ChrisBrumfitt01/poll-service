package com.dizplai.pollservice.service.aggregator;

import com.dizplai.pollservice.model.VoteResponse;
import com.dizplai.pollservice.model.VoteTotal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoteAggregatorTest {

    private VoteAggregator voteAggregator;

    @BeforeEach
    void setUp() {
        voteAggregator = new VoteAggregator();
    }

    @Test
    void getVoteTotals_shouldReturnExpectedValues() {
        LocalDateTime now = LocalDateTime.now();
        List<VoteResponse> votes = List.of(
                VoteResponse.builder().id(1L).selectedOptionId(7L).createdAt(now).build(),
                VoteResponse.builder().id(1L).selectedOptionId(7L).createdAt(now).build(),
                VoteResponse.builder().id(2L).selectedOptionId(8L).createdAt(now).build(),
                VoteResponse.builder().id(3L).selectedOptionId(9L).createdAt(now).build()
        );

        List<VoteTotal> result = voteAggregator.getVoteTotals(votes);
        VoteTotal option7 = result.stream()
                .filter(v -> v.getOptionId().equals(7L))
                .findFirst().get();
        VoteTotal option8 = result.stream()
                .filter(v -> v.getOptionId().equals(8L))
                .findFirst().get();
        VoteTotal option9 = result.stream()
                .filter(v -> v.getOptionId().equals(9L))
                .findFirst().get();

        assertThat(result).hasSize(3);
        assertThat(option7.getCount()).isEqualTo(2);
        assertThat(option7.getPercentage()).isEqualTo(50);
        assertThat(option8.getCount()).isEqualTo(1);
        assertThat(option8.getPercentage()).isEqualTo(25);
        assertThat(option9.getCount()).isEqualTo(1);
        assertThat(option9.getPercentage()).isEqualTo(25);
    }

    @Test
    void getVoteTotals_shouldReturnEmptyListWithoutException_whenGivenEmptyList() {
        List<VoteTotal> result = voteAggregator.getVoteTotals(new ArrayList<>());
        assertThat(result).isEmpty();
    }
}