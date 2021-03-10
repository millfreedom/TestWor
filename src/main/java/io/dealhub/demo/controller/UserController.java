package io.dealhub.demo.controller;


import io.dealhub.demo.controller.dto.UserDTO;
import io.dealhub.demo.repository.dao.User;
import io.dealhub.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        User user = userService.addUser(userDTO.getName(), userDTO.getEmail());
        return MapperDAOtoDTO.userDAOtoUserDTO(user);
    }

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return MapperDAOtoDTO.userDAOtoUserDTO(user);
    }
}
