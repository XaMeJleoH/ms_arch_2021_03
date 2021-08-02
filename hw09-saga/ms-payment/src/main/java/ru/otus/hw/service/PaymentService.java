package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.hw.model.Payment;
import ru.otus.hw.model.PaymentDTO;
import ru.otus.hw.repository.PaymentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final String URL_CANCEL_ORDER = "http://localhost:80/order/";

    private final PaymentRepository paymentRepository;

    public Long pay(Payment order) {
        var paymentDTO = paymentRepository.save(createPaymentDTO(order));
        if (Boolean.FALSE.equals(order.getSuccessPay())) {
            log.info("Payment is failed={}", paymentDTO);
            cancelOrder(order.getOrderId());
            throw new RuntimeException("Оплата не прошла");
        }
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

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_CANCEL_ORDER)
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
        paymentDTO.setCanceledPayment(true);
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

    public boolean getStatus(Long paymentId) {
        PaymentDTO paymentDTO = getPaymentDTOById(paymentId);
        return paymentDTO.getCanceledPayment() == null || Boolean.FALSE.equals(paymentDTO.getCanceledPayment());
    }

    private PaymentDTO getPaymentDTOById(Long paymentId) {
        var paymentDTO = paymentRepository.findById(paymentId);
        if (paymentDTO.isEmpty()) {
            throw new RuntimeException("Ой, что-то пошло не так, не найдена такая оплата");
        }
        return paymentDTO.get();
    }
}
