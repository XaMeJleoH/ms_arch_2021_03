package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.model.Payment;
import ru.otus.hw.service.PaymentService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public @ResponseBody
    Long pay(@RequestBody Payment payment) {
        log.info("Try to pay={}", payment);
        return paymentService.pay(payment);
    }

    @PostMapping
    public @ResponseBody
    boolean cancelPayment(@RequestBody Long paymentId) {
        log.info("Try to cancelPayment id={}", paymentId);
        return paymentService.cancelPayment(paymentId);
    }

}
