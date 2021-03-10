package io.dealhub.demo.service.conditions;

public interface Condition {
    boolean test(Long budget, Long min, Long max);
}
