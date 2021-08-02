package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.Order;
import ru.otus.hw.model.OrderDTO;
import ru.otus.hw.repository.OrderRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderRepository orderRepository;

    public Long pay(Order order) {
        var orderDTO = orderRepository.save(createOrderDTO(order));
        if (Boolean.FALSE.equals(order.getSuccessPay())) {
            log.info("Payment is failed={}", orderDTO);
            throw new RuntimeException("Оплата не прошла");
        }
        log.info("Payment is success={}", orderDTO);
        return orderDTO.getId();
    }

    public boolean cancelPayment(Long orderId) {
        var orderDTO = orderRepository.findById(orderId);
        if (orderDTO.isEmpty()) {
            throw new RuntimeException("Ой, что-то пошло не так, не найдена такая оплата");
        }
        orderDTO.get().setCanceledPayment(true);
        orderRepository.save(orderDTO.get());
        log.info("Payment is canceled={}", orderDTO);
        return true;
    }

    private OrderDTO createOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(order.getUserId());
        orderDTO.setPremium(order.getPremium());
        orderDTO.setSuccess(order.getSuccessPay());
        return orderDTO;
    }
}
