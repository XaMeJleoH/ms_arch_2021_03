package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserIdempotentRepository extends CrudRepository<UserIdempotentDTO, Long> {
    boolean existsUserIdempotentDTOByIdempotencyKeyAndUserId(UUID key, Long userId);
}
