package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.model.Payment;
import ru.otus.hw.service.PaymentService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public @ResponseBody
    Long pay(@RequestBody Payment payment) {
        log.info("Try to pay={}", payment);
        return paymentService.pay(payment);
    }


    @PostMapping("/{paymentId}")
    public @ResponseBody
    boolean cancelPayment(@PathVariable Long paymentId) {
        log.info("Try to cancelPayment id={}", paymentId);
        return paymentService.cancelPayment(paymentId);
    }

    @GetMapping("/{paymentId}")
    public boolean getStatus(@PathVariable Long paymentId) {
        log.info("Try to get status for paymentId={}", paymentId);
        return paymentService.getStatus(paymentId);
    }
}
