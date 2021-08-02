package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.Payment;
import ru.otus.hw.model.PaymentDTO;
import ru.otus.hw.repository.PaymentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Long pay(Payment order) {
        var paymentDTO = paymentRepository.save(createPaymentDTO(order));
        if (Boolean.FALSE.equals(order.getSuccessPay())) {
            log.info("Payment is failed={}", paymentDTO);
            throw new RuntimeException("Оплата не прошла");
        }
        log.info("Payment is success={}", paymentDTO);
        return paymentDTO.getId();
    }

    public boolean cancelPayment(Long orderId) {
        var paymentDTO = paymentRepository.findById(orderId);
        if (paymentDTO.isEmpty()) {
            throw new RuntimeException("Ой, что-то пошло не так, не найдена такая оплата");
        }
        paymentDTO.get().setCanceledPayment(true);
        paymentRepository.save(paymentDTO.get());
        log.info("Payment is canceled={}", paymentDTO);
        return true;
    }

    private PaymentDTO createPaymentDTO(Payment order) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setUserId(order.getUserId());
        paymentDTO.setPremium(order.getPremium());
        paymentDTO.setSuccess(order.getSuccessPay());
        return paymentDTO;
    }
}
