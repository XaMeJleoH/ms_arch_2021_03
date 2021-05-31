package ru.otus.hw.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.model.User;
import ru.otus.hw.service.AuthService;
import ru.otus.hw.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpServletResponse httpServletResponse) {
        return authService.login(user, httpServletResponse);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestHeader Map<String, String> headers) {
        return authService.auth(headers);
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader Map<String, String> headers, HttpServletResponse httpServletResponse) {
        return authService.logout(headers, httpServletResponse);
    }

    @PostMapping("/registration")
    public @ResponseBody
    User createUser(@RequestBody User user) {
        log.info("Try to create User={}", user);
        return userService.createUser(user);
    }

}
