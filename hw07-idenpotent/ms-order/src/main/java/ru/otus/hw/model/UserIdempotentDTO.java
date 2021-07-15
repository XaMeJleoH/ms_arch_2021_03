package ru.otus.hw.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_idempotent", schema = "ms_order")
public class UserIdempotentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "IDEMPOTENCY_KEY")
    private UUID idempotencyKey;

    public UserIdempotentDTO(Long userId, UUID idempotencyKey) {
        this.userId = userId;
        this.idempotencyKey = idempotencyKey;
    }
}
