package ru.otus.hw.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private Long userId;
    private String orderName;
    private BigDecimal amount;

}
