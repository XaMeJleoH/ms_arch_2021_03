package ru.otus.hw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "payment", schema = "ms_payment")
public class PaymentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "PREMIUM")
    private BigDecimal premium;

    @Column(name = "SUCCESS")
    private Boolean success;

    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;
}
