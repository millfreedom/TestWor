package io.dealhub.demo.service;

import io.dealhub.demo.repository.dao.User;

public interface UserService {
    User addUser(String userName, String email);

    User getUser(Long userId);
}