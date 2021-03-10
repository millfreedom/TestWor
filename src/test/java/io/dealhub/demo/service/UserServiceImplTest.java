package io.dealhub.demo.service;

import io.dealhub.demo.repository.UserRepository;
import io.dealhub.demo.repository.dao.User;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        String name = "user1";
        String email = "user1@email.com";

        assertEquals(0, userRepository.findAll().size());

        userService.addUser(name, email);
        assertEquals(1, userRepository.findAll().size());

        User user = userRepository.findById(0L).get();
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    @SneakyThrows
    @Test
    public void addMultiUser() {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> userService.addUser("user" + finalI, "email" + finalI + "email.com")).start();
        }
        Thread.sleep(1000);
        assertEquals(10, userRepository.findAll().size());
    }
}