package io.dealhub.demo.service;

import io.dealhub.demo.repository.dao.Rule;

import java.util.List;

public interface RuleService {
    Rule addRule(String condition, Long min, Long max, List<Long> verifiers);

    List<Rule> getAll();
}
