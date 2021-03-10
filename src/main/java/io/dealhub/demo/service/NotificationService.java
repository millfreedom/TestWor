package io.dealhub.demo.service;

import io.dealhub.demo.repository.dao.Chain;

import java.util.List;

public interface NotificationService {
    void sendNotification(Long planId, List<Chain> chains);
}
