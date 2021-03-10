package io.dealhub.demo.service.conditions;

import org.springframework.stereotype.Service;

@Service
public class Between implements Condition {
    @Override
    public boolean test(Long budget, Long min, Long max) {
        return budget > min && budget < max;
    }
}
