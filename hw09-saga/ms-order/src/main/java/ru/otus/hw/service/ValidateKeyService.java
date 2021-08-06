package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.Order;
import ru.otus.hw.model.OrderDTO;
import ru.otus.hw.repository.OrderRepository;
import ru.otus.hw.repository.UserIdempotentRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidateKeyService {
    private final UserIdempotentRepository userIdempotentRepository;

    public void validate(UUID key, Long userId) {
        if (userIdempotentRepository.existsUserIdempotentDTOByIdempotencyKeyAndUserId(key, userId)) {
            throw new RuntimeException("Данный заказ уже в работе");
        }
    }
}
