package com.dizplai.pollservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollResponse {

    private Long id;
    private String question;
    private List<OptionResponse> options;
    private LocalDateTime createdAt;

}
