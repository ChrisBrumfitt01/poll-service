package com.dizplai.pollservice.service.mapper;

import com.dizplai.pollservice.entity.VoteEntity;
import com.dizplai.pollservice.model.VoteResponse;
import com.dizplai.pollservice.model.VotesResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoteMapper {

    public VoteResponse toVoteResponse(VoteEntity voteEntity){
        return VoteResponse.builder()
                .id(voteEntity.getId())
                .selectedOptionId(voteEntity.getSelectedOption().getId())
                .selectedOption(voteEntity.getSelectedOption().getDescription())
                .createdAt(voteEntity.getCreatedAt())
                .build();
    }

}
