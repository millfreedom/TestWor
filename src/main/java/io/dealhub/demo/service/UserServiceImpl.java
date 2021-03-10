package io.dealhub.demo.service;

import io.dealhub.demo.repository.UserRepository;
import io.dealhub.demo.repository.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(String userName, String email) {
        User newUser = User.builder().name(userName).email(email).build();
        return userRepository.save(newUser);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id: " + userId + " not found"));
    }
}
