package ru.otus.hw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order", schema = "ms_order")
public class PaymentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "PREMIUM")
    private BigDecimal premium;

    @Column(name = "SUCCESS")
    private Boolean success;

    @Column(name = "CANCELED_PAYMENT")
    private Boolean canceledPayment;
}
