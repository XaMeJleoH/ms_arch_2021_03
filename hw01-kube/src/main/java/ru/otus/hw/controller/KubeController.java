package ru.otus.hw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hw")
public class KubeController {

    @GetMapping(name = "/hello", produces = "application/json")
    public String getVersion() {
        return "Hello, nice to meet you";
    }
}
