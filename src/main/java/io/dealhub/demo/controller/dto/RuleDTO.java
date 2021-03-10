package io.dealhub.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RuleDTO {
    private Long ruleId;
    private String condition;
    private Long min = 0L;
    private Long max = Long.MAX_VALUE;
    private List<Long> verifiers;
}
