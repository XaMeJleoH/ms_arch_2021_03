package ru.otus.hw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "shipment", schema = "ms_shipment")
public class ShipmentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "RESERVE_WAREHOUSE_ID")
    private Long reserveWarehouseId;

    @Column(name = "address")
    private String address;

    @Column(name = "SUCCESS")
    private Boolean success;

    @Column(name = "SHIPMENT_STATUS")
    private ShipmentStatus shipmentStatus;
}
