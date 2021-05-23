package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.otus.hw.model.User;
import ru.otus.hw.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("proxy")
public class ProxyController {

    @GetMapping(name = "/", produces = "application/json")
    public String getUser(@RequestParam("url") String url) {
        log.info("Try proxy to url={}", url);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        log.info("Result proxy={}", result);
        return result;
    }

}
