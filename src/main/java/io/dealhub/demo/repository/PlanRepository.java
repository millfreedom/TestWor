package io.dealhub.demo.repository;

import io.dealhub.demo.repository.dao.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
