package com.dizplai.pollservice.service.mapper;

import com.dizplai.pollservice.entity.OptionEntity;
import com.dizplai.pollservice.entity.PollEntity;
import com.dizplai.pollservice.model.OptionResponse;
import com.dizplai.pollservice.model.PollRequest;
import com.dizplai.pollservice.model.PollResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PollMapper {

    public PollEntity toPollEntity(PollRequest pollRequest) {
        PollEntity pollEntity = new PollEntity();
        pollEntity.setQuestion(pollRequest.getQuestion());
        pollEntity.setActiveFrom(pollRequest.getActiveFrom());
        pollEntity.setActiveTo(pollRequest.getActiveTo());

        pollRequest.getOptions().forEach(optionDescription -> {
            OptionEntity optionEntity = new OptionEntity();
            optionEntity.setDescription(optionDescription);
            pollEntity.addOption(optionEntity);
        });

        return pollEntity;
    }


    public PollResponse toPollResponse(PollEntity pollEntity) {
        List<OptionResponse> options = pollEntity.getOptions().stream().map(optionEntity -> {
            return OptionResponse.builder()
                    .id(optionEntity.getId())
                    .description(optionEntity.getDescription())
                    .createdAt(pollEntity.getCreatedAt())
                    .build();
        }).toList();

        return PollResponse.builder()
                .id(pollEntity.getId())
                .question(pollEntity.getQuestion())
                .options(options)
                .createdAt(pollEntity.getCreatedAt())
                .activeFrom(pollEntity.getActiveFrom())
                .activeTo(pollEntity.getActiveTo())
                .build();
    }

}
