package com.dizplai.pollservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {

    private Long id;
    private Long selectedOptionId;
    private String selectedOption;
    private LocalDateTime createdAt;

}
