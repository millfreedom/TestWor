package io.dealhub.demo.service;

import io.dealhub.demo.repository.PlanRepository;
import io.dealhub.demo.repository.UserRepository;
import io.dealhub.demo.repository.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationImpl implements NotificationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanRepository planRepository;

    @Override
    public void sendNotification(Long planId, List<Chain> chains) {
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plan id: " + planId + " not found"));
        chains.forEach(c -> {
            for (int i = 0; i < c.getVerifiers().size(); i++) {
                Verifier verifier = c.getVerifiers().get(i);
                if (!verifier.isNotified() &&
                        verifier.getVerified().equals(VerificationState.IN_PENDING)) {
                    notifyUser(verifier.getUserId(), plan, c.getChainId());
                    verifier.setNotified(true);
                    verifier.setDateNotification(LocalDateTime.now());
                    planRepository.save(plan);
                }
            }
        });
    }

    private void notifyUser(Long userId, Plan plan, Long chainId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id: " + userId + " not found"));
        System.out.println("Send to user: " + user.getName()
                + " Plan: " + plan.getPlanId()
                + " Chain: " + chainId
                + " with budget: " + plan.getBudget()
                + " with status: " + plan.getVerificationState()
                + " with verifiers: "
        );
        plan.getChains().forEach(c ->
                c.getVerifiers().forEach(v ->
                        System.out.print(
                                "User: " + userRepository.findById(v.getUserId()).get().getName() + "\n" +
                                        "Status: notification - " + v.isNotified() + " verification - " + v.getVerified().name() + (v.getReason() == null ? "" : " reason - " + v.getReason()) + "\n"
                        )
                )
        );
    }
}
