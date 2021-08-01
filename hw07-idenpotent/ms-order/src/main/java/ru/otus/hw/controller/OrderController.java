package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.model.Order;
import ru.otus.hw.service.OrderService;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public @ResponseBody
    Long createUser(@RequestBody Order order) {
        log.info("Try to create Order={}", order);
        return orderService.createOrder(order);
    }
}
