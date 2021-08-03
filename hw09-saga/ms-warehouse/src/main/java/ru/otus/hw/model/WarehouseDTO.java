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

    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    @Column(name = "ORDER_NAME")
    private String orderName;

    @Column(name = "SUCCESS")
    private Boolean success;

    @Column(name = "WAREHOUSE_STATUS")
    private WarehouseStatus warehouseStatus;
}
