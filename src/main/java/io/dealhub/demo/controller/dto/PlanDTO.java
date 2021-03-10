package io.dealhub.demo.controller.dto;

import io.dealhub.demo.repository.dao.VerificationState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PlanDTO {
    private Long planId;
    private long budget;
    private VerificationState planState;
    private List<ChainDTO> verifiers;
}
