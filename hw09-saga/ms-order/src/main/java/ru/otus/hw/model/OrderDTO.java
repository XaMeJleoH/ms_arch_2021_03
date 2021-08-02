package ru.otus.hw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order", schema = "ms_order")
public class OrderDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ORDER_NAME")
    private String orderName;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "SUCCESS")
    private Boolean success;

    @Column(name = "CANCELED_ORDER")
    private Boolean canceledOrder;

}
