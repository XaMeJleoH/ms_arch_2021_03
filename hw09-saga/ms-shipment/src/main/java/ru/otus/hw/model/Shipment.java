package ru.otus.hw.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Validated
public class Shipment {
    @NotNull
    @Min(1)
    private Long userId;

    @NotNull
    @Min(1)
    private Long orderId;

    @NotNull
    @Min(1)
    private Long reserveWarehouseId;

    @NotBlank
    private String address;

    @NotNull
    private Boolean successReserve;

}
