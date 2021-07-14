package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.model.UserIdempotentDTO;

import java.util.UUID;

@Repository
public interface UserIdempotentRepository extends CrudRepository<UserIdempotentDTO, Long> {
    boolean existsUserIdempotentDTOByIdempotencyKeyAndUserId(UUID key, Long userId);
}
