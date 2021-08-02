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
public class Warehouse {
    @NotNull
    @Min(1)
    private Long userId;

    @NotNull
    @Min(1)
    private Long paymentId;

    @NotBlank
    private String orderName;

    @NotNull
    private Boolean successReserve;

}
