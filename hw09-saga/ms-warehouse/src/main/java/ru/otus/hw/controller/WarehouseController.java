package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.model.Warehouse;
import ru.otus.hw.service.WarehouseService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping
    public @ResponseBody
    Long reserve(@RequestBody Warehouse warehouse) {
        log.info("Try to reserve in Warehouse, warehouse={}", warehouse);
        return warehouseService.reserve(warehouse);
    }

    @PostMapping
    public @ResponseBody
    boolean cancelReserve(@RequestBody Long reserveId) {
        log.info("Try to cancelReserve id={}", reserveId);
        return warehouseService.cancelReserve(reserveId);
    }

}
