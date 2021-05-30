package ru.otus.hw.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.model.User;
import ru.otus.hw.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class CrudController {

    private final UserService userService;

    @PostMapping("registration")
    public @ResponseBody
    User createUser(@RequestBody User user) {
        log.info("Try to create User={}", user);
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Integer userId) {
        log.info("Try to get userId={}", userId);
        return userService.getUser(userId);
    }

    @PostMapping("/{userId}")
    public @ResponseBody
    boolean deleteUser(@PathVariable Integer userId) {
        log.info("Try to delete userId={}", userId);
        return userService.deleteUser(userId);
    }

    @PutMapping
    public @ResponseBody
    boolean updateUser(@RequestBody User user) {
        log.info("Try to update User={}", user);
        return userService.updateUser(user);
    }


}
