package io.dealhub.demo.controller;

import io.dealhub.demo.controller.dto.RuleDTO;
import io.dealhub.demo.repository.dao.Rule;
import io.dealhub.demo.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rule")
public class RuleController {
    @Autowired
    RuleService ruleService;

    @PostMapping("/create")
    RuleDTO addRule(@RequestBody RuleDTO ruleDTO) {
        Rule rule = ruleService.addRule(ruleDTO.getCondition(), ruleDTO.getMin(), ruleDTO.getMax(), ruleDTO.getVerifiers());
        return MapperDAOtoDTO.ruleDAOtoRuleDTO(rule);
    }
}
