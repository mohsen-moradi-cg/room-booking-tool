package com.RoomBookingTool.Server.controllers;

import com.RoomBookingTool.Server.models.User;
import com.RoomBookingTool.Server.repositories.UserJpaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @GetMapping
    public List<User> list() {
        return userJpaRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{userId}")
    public Optional<User> getUserByUserId(@PathVariable Long userId) {
        return userJpaRepository.findById(userId);
    }

    @GetMapping
    @RequestMapping("/email/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        System.out.println(email);
        return userJpaRepository.findByEmailContains(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody final User user){
        user.setCreatedAt(LocalDateTime.now());
        return userJpaRepository.saveAndFlush(user);
    }

    @RequestMapping( value = "{userId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long userId) {
        userJpaRepository.deleteById(userId);
    }

    @RequestMapping( value = "{userId}", method = RequestMethod.PUT)
    public User update(@PathVariable Long userId, @RequestBody User user) {
        User existingUser = userJpaRepository.getOne(userId);
        BeanUtils.copyProperties(user, existingUser, "userId");
        return userJpaRepository.saveAndFlush(existingUser);
    }
}
