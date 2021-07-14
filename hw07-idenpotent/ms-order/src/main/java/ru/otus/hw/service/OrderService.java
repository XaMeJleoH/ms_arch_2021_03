package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.Order;
import ru.otus.hw.model.OrderDTO;
import ru.otus.hw.model.UserIdempotentDTO;
import ru.otus.hw.repository.OrderRepository;
import ru.otus.hw.repository.UserIdempotentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserIdempotentRepository userIdempotentRepository;
    private final ValidateKeyService validateKeyService;

    public Long createOrder(Order order) {
        validateKeyService.validate(order.getIdempotencyKey(), order.getUserId());
        var orderDTO = orderRepository.save(createOrderDTO(order));
        userIdempotentRepository.save(new UserIdempotentDTO(order.getUserId(), order.getIdempotencyKey()));
        log.info("Created order={}", orderDTO);
        return orderDTO.getId();
    }

    private OrderDTO createOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(order.getUserId());
        orderDTO.setOrderName(order.getOrderName());
        orderDTO.setAmount(order.getAmount());
        return orderDTO;
    }
}
