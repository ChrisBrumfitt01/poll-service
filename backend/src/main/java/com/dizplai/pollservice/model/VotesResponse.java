package com.dizplai.pollservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotesResponse {

    private Long pollId;
    private List<VoteResponse> votes;

}
