package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.dto.UserResponseDTO;
import com.alpesh.creditscope.entity.User;
import com.alpesh.creditscope.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //For Getting All User
    @GetMapping
    public List<UserResponseDTO> getUsers() {
        return userService.getAllUsers();
    }

    //For Getting User By Id
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    //For Creating The User
    @PostMapping
    public UserResponseDTO createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    //updating The User
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    //Deleting The User
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}