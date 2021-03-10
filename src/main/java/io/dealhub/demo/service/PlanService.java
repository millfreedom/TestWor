package io.dealhub.demo.service;

import io.dealhub.demo.controller.dto.Action;
import io.dealhub.demo.repository.dao.Plan;

public interface PlanService {
    Plan addPlan(long budget);

    Plan verifyingPlan(Long planId, Long chainId, Long userId, Action action, String comment);

    Plan getPlan(Long planId);
}
