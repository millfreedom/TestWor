package io.dealhub.demo.service;

import io.dealhub.demo.controller.dto.Action;
import io.dealhub.demo.repository.ChainRepository;
import io.dealhub.demo.repository.PlanRepository;
import io.dealhub.demo.repository.dao.Chain;
import io.dealhub.demo.repository.dao.Plan;
import io.dealhub.demo.repository.dao.VerificationState;
import io.dealhub.demo.repository.dao.Verifier;
import io.dealhub.demo.service.conditions.Condition;
import io.dealhub.demo.service.conditions.ConditionFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {
    private static Logger logger = LoggerFactory.getLogger(PlanService.class);
    @Autowired
    private ConditionFinder conditionFinder;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ChainRepository chainRepository;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private NotificationService notificationService;


    @Override
    public Plan addPlan(long budget) {
        logger.info("Start adding plan with budget:" + budget);
        Plan newPlan = Plan.builder()
                .budget(budget)
                .verificationState(VerificationState.IN_PENDING)
                .chains(getChains(budget))
                .build();
        newPlan = planRepository.save(newPlan);
        logger.info("Saved plan to DB:" + newPlan.getPlanId());
        notificationService.sendNotification(newPlan.getPlanId(), newPlan.getChains());
        return newPlan;
    }

    private List<Chain> getChains(long budget) {
        return ruleService.getAll().stream()
                .filter(r -> {
                    Condition condition = conditionFinder.getCondition(r.getCondition());
                    return condition.test(budget, r.getMin(), r.getMax());
                })
                .map(r -> {
                            Chain chain = Chain.builder()
                                    .verifiers(r.getVerifiersIds().stream()
                                            .map(i -> Verifier.builder()
                                                    .userId(i)
                                                    .notified(false)
                                                    .verified(VerificationState.IN_PENDING)
                                                    .build())
                                            .collect(Collectors.toList())
                                    )
                                    .build();
                            chain = chainRepository.save(chain);
                            logger.info("Chain created: " + chain.getChainId() + " for budget: " + budget + " from rule: " + r.getRuleId());
                            return chain;
                        }
                )
                .collect(Collectors.toList());

    }

    @Override
    public Plan verifyingPlan(Long planId, Long chainId, Long userId, Action action, String comment) {
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan id: " + planId + " not found"));
        if (!plan.getVerificationState().equals(VerificationState.IN_PENDING)) {
            logger.warn("Plan status is: " + plan.getVerificationState() + ", respond BAD_REQUEST");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan has status: " + plan.getVerificationState().name());
        }
        List<Chain> chains = plan.getChains();
        Chain chain = chains.stream().filter(c -> c.getChainId().equals(chainId))
                .findFirst().orElseThrow(() -> {
                    logger.warn("Chain id is invalid: " + chainId + ", respond BAD_REQUEST");
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chain id: " + chainId + " not found");
                });

        Verifier verifier = chain.getVerifiers().stream().filter(v -> v.getUserId().equals(userId))
                .findFirst().orElseThrow(() -> {
                    logger.warn("User id is invalid: " + userId + ", respond BAD_REQUEST");
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id: " + userId + " not found in chain: " + chainId);
                });

        if (!verifier.getVerified().equals(VerificationState.IN_PENDING)) {
            logger.warn("Status of verifier is: " + verifier.getVerified() + "(not IN_PENDING), respond BAD_REQUEST");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id: " + userId + "has already done the choice: " + verifier.getVerified());
        }
        logger.info("Action is: " + action);
        verifier.setDateVerification(LocalDateTime.now());
        if (action.equals(Action.APPROVE)) {
            verifier.setVerified(VerificationState.APPROVED);
            if (chains.stream().noneMatch(c -> c.getVerifiers().stream().anyMatch(v -> !v.getVerified().equals(VerificationState.APPROVED)))) {
                plan.setVerificationState(VerificationState.APPROVED);
                logger.info("Plan is approved");
            }
        }

        if (action.equals(Action.REJECT)) {
            if (comment == null || comment.isEmpty()) {
                logger.warn("The comment is null or empty:\"" + comment + "\", respond BAD_REQUEST");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The comment is mandatory");
            }
            verifier.setVerified(VerificationState.REJECTED);
            verifier.setReason(comment);
            plan.setVerificationState(VerificationState.REJECTED);
            logger.info("Plan: " + plan.getPlanId() + " is rejected");
        }
        plan = planRepository.save(plan);
        return plan;
    }

    @Override
    public Plan getPlan(Long planId) {
        return planRepository.findById(planId).orElseThrow(() -> {
            logger.warn("Plan with id: " + planId + " not found");
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan with id: " + planId + " not found");
        });
    }
}
