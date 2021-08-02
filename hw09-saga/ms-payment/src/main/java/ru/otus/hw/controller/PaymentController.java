package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.model.Order;
import ru.otus.hw.service.PaymentService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public @ResponseBody
    Long pay(@RequestBody Order order) {
        log.info("Try to pay Order={}", order);
        return paymentService.pay(order);
    }

    @PostMapping
    public @ResponseBody
    boolean cancelPayment(@RequestBody Long orderId) {
        log.info("Try to cancelPayment id={}", orderId);
        return paymentService.cancelPayment(orderId);
    }

}
