package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.Order;
import ru.otus.hw.model.OrderDTO;
import ru.otus.hw.model.OrderStatus;
import ru.otus.hw.model.UserIdempotentDTO;
import ru.otus.hw.repository.OrderRepository;
import ru.otus.hw.repository.UserIdempotentRepository;

import java.util.Optional;

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
        if (Boolean.FALSE.equals(order.getSuccess())) {
            log.info("Order create is failed={}", orderDTO);
            orderDTO.setOrderStatus(OrderStatus.FAILED);
            orderRepository.save(orderDTO);
            throw new RuntimeException("Заказ не был создан");
        }
        orderDTO.setOrderStatus(OrderStatus.IN_PROGRESS);
        userIdempotentRepository.save(new UserIdempotentDTO(order.getUserId(), order.getIdempotencyKey()));
        log.info("Created order={}", orderDTO);
        return orderDTO.getId();
    }

    private OrderDTO createOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(order.getUserId());
        orderDTO.setOrderName(order.getOrderName());
        orderDTO.setAmount(order.getAmount());
        orderDTO.setSuccess(order.getSuccess());
        return orderDTO;
    }

    public boolean cancelOrder(Long orderId) {
        OrderDTO orderDTO = getOrderDTO(orderId);
        orderDTO.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(orderDTO);
        log.info("Order is canceled={}", orderDTO);
        return true;
    }

    private OrderDTO getOrderDTO(Long orderId) {
        var orderDTO = orderRepository.findById(orderId);
        if (orderDTO.isEmpty()) {
            throw new RuntimeException("Ой, что-то пошло не так, не найден такой заказ");
        }
        return orderDTO.get();
    }

    public OrderStatus getStatus(Long orderId) {
        OrderDTO orderDTO = getOrderDTO(orderId);
        return orderDTO.getOrderStatus();
    }

    public void finishOrder(Long orderId) {
        OrderDTO orderDTO = getOrderDTO(orderId);
        orderDTO.setOrderStatus(OrderStatus.FINISH);
        orderRepository.save(orderDTO);
    }
}

