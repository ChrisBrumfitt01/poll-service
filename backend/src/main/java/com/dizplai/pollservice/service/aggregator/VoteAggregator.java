package com.dizplai.pollservice.service.aggregator;

import com.dizplai.pollservice.model.VoteResponse;
import com.dizplai.pollservice.model.VoteTotal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class VoteAggregator {

    public List<VoteTotal> getVoteTotals(List<VoteResponse> votes) {
        Map<Long, Long> counts = votes.stream()
                .collect(Collectors.groupingBy(VoteResponse::getSelectedOptionId, Collectors.counting()));
        return counts.entrySet().stream()
                .map(entry -> {
                    long optionId = entry.getKey();
                    long count = entry.getValue();
                    double percentage = count * 100.0 / votes.size();
                    return new VoteTotal(optionId, count, Math.round(percentage * 10.0) / 10.0);
                })
                .collect(Collectors.toList());
    }

}
