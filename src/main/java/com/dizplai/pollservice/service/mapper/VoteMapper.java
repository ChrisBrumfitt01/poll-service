package com.dizplai.pollservice.service.mapper;

import com.dizplai.pollservice.entity.VoteEntity;
import com.dizplai.pollservice.model.VoteResponse;
import com.dizplai.pollservice.model.VotesResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoteMapper {

    public VotesResponse toVotesResponse(Long pollId, List<VoteEntity> voteEntities) {
        List<VoteResponse> voteResponses = voteEntities.stream().map(e -> {
            return VoteResponse.builder()
                    .id(e.getId())
                    .selectedOptionId(e.getSelectedOptionId())
                    .selectedOption(e.getSelectedOption().getDescription())
                    .createdAt(e.getCreatedAt())
                    .build();
        }).toList();

        return new VotesResponse(pollId, voteResponses);
    }



}
