package io.dealhub.demo.controller;

import io.dealhub.demo.controller.dto.ChainDTO;
import io.dealhub.demo.controller.dto.PlanDTO;
import io.dealhub.demo.controller.dto.RuleDTO;
import io.dealhub.demo.controller.dto.UserDTO;
import io.dealhub.demo.repository.dao.Plan;
import io.dealhub.demo.repository.dao.Rule;
import io.dealhub.demo.repository.dao.User;

import java.util.stream.Collectors;

public class MapperDAOtoDTO {
    public static UserDTO userDAOtoUserDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static PlanDTO planDAOtoPlanDTO(Plan plan) {
        return PlanDTO.builder()
                .planId(plan.getPlanId())
                .budget(plan.getBudget())
                .planState(plan.getVerificationState())
                .verifiers(plan.getChains().stream()
                        .map(c -> ChainDTO.builder()
                                .chainId(c.getChainId())
                                .verifiers(c.getVerifiers())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public static RuleDTO ruleDAOtoRuleDTO(Rule rule) {
        return RuleDTO.builder()
                .ruleId(rule.getRuleId())
                .condition(rule.getCondition())
                .min(rule.getMin())
                .max(rule.getMax())
                .verifiers(rule.getVerifiersIds())
                .build();
    }
}
