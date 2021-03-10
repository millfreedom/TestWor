package io.dealhub.demo.service.conditions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ConditionFinder {
    @Autowired
    private List<Condition> conditions;

    public Condition getCondition(String condition) {
        return conditions.stream()
                .filter(c -> c.getClass().getSimpleName().equalsIgnoreCase(condition))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Condition not found"));
    }
}
