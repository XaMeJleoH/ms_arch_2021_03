package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.model.User;
import ru.otus.hw.service.UserService;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class CrudController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Integer userId, @RequestHeader Map<String, String> headers) {
        log.info("Try to get userId={}", userId);
        return userService.getUser(userId, headers);
    }

    @PostMapping("/{userId}")
    public @ResponseBody
    boolean deleteUser(@PathVariable Integer userId, @RequestHeader Map<String, String> headers) {
        log.info("Try to delete userId={}", userId);
        return userService.deleteUser(userId, headers);
    }

    @PutMapping
    public @ResponseBody
    boolean updateUser(@RequestBody User user, @RequestHeader Map<String, String> headers) {
        log.info("Try to update User={}", user);
        return userService.updateUser(user, headers);
    }


}
