package io.dealhub.demo.controller;

import io.dealhub.demo.controller.dto.PlanDTO;
import io.dealhub.demo.controller.dto.ReactionDTO;
import io.dealhub.demo.repository.dao.Plan;
import io.dealhub.demo.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plan")
public class PlanController {
    @Autowired
    private PlanService planService;

    @PostMapping("/create/{budget}")
    public PlanDTO addPlan(@PathVariable Long budget) {
        Plan plan = planService.addPlan(budget);
        return MapperDAOtoDTO.planDAOtoPlanDTO(plan);
    }

    @PutMapping("/{planId}")
    public PlanDTO verifyingPlan(@PathVariable Long planId, @RequestBody ReactionDTO reactionDTO) {
        Plan plan = planService.verifyingPlan(planId, reactionDTO.getChainId(), reactionDTO.getUserId(), reactionDTO.getAction(), reactionDTO.getComment());
        return MapperDAOtoDTO.planDAOtoPlanDTO(plan);
    }

    @GetMapping("/{planId}")
    public PlanDTO getPlan(@PathVariable Long planId) {
        Plan plan = planService.getPlan(planId);
        return MapperDAOtoDTO.planDAOtoPlanDTO(plan);
    }
}
