package io.dealhub.demo.service;

import io.dealhub.demo.repository.RulesRepository;
import io.dealhub.demo.repository.dao.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {
    @Autowired
    private RulesRepository rulesRepository;

    @Autowired
    private UserService userService;


    @Override
    public Rule addRule(String condition, Long min, Long max, List<Long> verifiers) {
        verifiers.forEach(userService::getUser);
        Rule newRule = Rule.builder()
                .condition(condition)
                .min(min)
                .max(max)
                .verifiersIds(verifiers)
                .build();
        return rulesRepository.save(newRule);
    }

    @Override
    public List<Rule> getAll() {
        return rulesRepository.findAll();
    }
}
