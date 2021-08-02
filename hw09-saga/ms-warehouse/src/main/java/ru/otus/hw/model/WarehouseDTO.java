package ru.otus.hw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "warehouse", schema = "ms_warehouse")
public class WarehouseDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ORDER_NAME")
    private String orderName;

    @Column(name = "SUCCESS")
    private Boolean success;

    @Column(name = "CANCELED_RESERVE")
    private Boolean canceledReserve;
}
