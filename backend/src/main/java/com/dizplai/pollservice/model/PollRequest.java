package com.dizplai.pollservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollRequest {

    @NotNull(message = "'question' must not be null")
    private String question;

    @NotNull(message = "'options' must not be null")
    @Size(min = 2, max = 7, message = "'options' must contain between 2 and 7 elements")
    private List<String> options;

    @NotNull(message = "'activeFrom' must be provided in the format YYYY/MM/DD'T'tt:mm")
    private LocalDateTime activeFrom;

    @NotNull(message = "'activeTo' must be provided in the format YYYY/MM/DD'T'tt:mm")
    private LocalDateTime activeTo;

}
