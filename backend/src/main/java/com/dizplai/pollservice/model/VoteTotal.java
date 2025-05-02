package com.dizplai.pollservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteTotal {
    private Long optionId;
    private Long count;
    private Double percentage;
}
