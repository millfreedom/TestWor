package io.dealhub.demo.repository;

import io.dealhub.demo.repository.dao.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RulesRepository extends JpaRepository<Rule, Long> {
}
