package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.hw.model.Payment;
import ru.otus.hw.model.PaymentDTO;
import ru.otus.hw.model.PaymentStatus;
import ru.otus.hw.repository.PaymentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${ms.order.url.cancel.order:http://localhost:80/order/}")
    private String urlCancelOrder;

    private final PaymentRepository paymentRepository;

    public Long pay(Payment order) {
        var paymentDTO = paymentRepository.save(createPaymentDTO(order));
        if (Boolean.FALSE.equals(order.getSuccessPay())) {
            log.info("Payment is failed={}", paymentDTO);
            paymentDTO.setPaymentStatus(PaymentStatus.FAILED);
            paymentRepository.save(paymentDTO);
            cancelOrder(order.getOrderId());
            throw new RuntimeException("Оплата не прошла");
        }
        paymentDTO.setPaymentStatus(PaymentStatus.FINISH);
        paymentRepository.save(paymentDTO);
        log.info("Payment is success={}", paymentDTO);
        return paymentDTO.getId();
    }

    private void cancelOrder(Long orderId) {
        log.info("Отменяем заказ");
        //call cancel order
        ResponseEntity<String> response = callService(orderId);
        log.info("Result call={}", response);
    }

    private ResponseEntity<String> callService(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlCancelOrder)
                .path(id.toString());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(
                builder.toUriString(),
                entity,
                String.class);
    }

    public boolean cancelPayment(Long paymentId) {
        PaymentDTO paymentDTO = getPaymentDTOById(paymentId);
        paymentDTO.setPaymentStatus(PaymentStatus.CANCELED);
        paymentRepository.save(paymentDTO);
        log.info("Payment is canceled={}", paymentDTO);
        cancelOrder(paymentDTO.getOrderId());
        return true;
    }

    private PaymentDTO createPaymentDTO(Payment order) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setUserId(order.getUserId());
        paymentDTO.setOrderId(order.getOrderId());
        paymentDTO.setPremium(order.getPremium());
        paymentDTO.setSuccess(order.getSuccessPay());
        return paymentDTO;
    }

    public PaymentStatus getStatus(Long paymentId) {
        PaymentDTO paymentDTO = getPaymentDTOById(paymentId);
        return paymentDTO.getPaymentStatus();
    }

    private PaymentDTO getPaymentDTOById(Long paymentId) {
        var paymentDTO = paymentRepository.findById(paymentId);
        if (paymentDTO.isEmpty()) {
            throw new RuntimeException("Ой, что-то пошло не так, не найдена такая оплата");
        }
        return paymentDTO.get();
    }
}
