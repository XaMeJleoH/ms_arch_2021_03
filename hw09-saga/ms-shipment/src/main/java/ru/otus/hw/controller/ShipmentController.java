package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.model.Shipment;
import ru.otus.hw.service.ShipmentService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("shipment")
public class ShipmentController {
    private final ShipmentService shipmentService;

    @PostMapping
    public @ResponseBody
    Long reserveShipment(@RequestBody Shipment shipment) {
        log.info("Try to reserve Shipment, warehouse={}", shipment);
        return shipmentService.reserveShipment(shipment);
    }


    @PostMapping("/{shipmentId}")
    public @ResponseBody
    boolean cancelReserveShipment(@PathVariable Long shipmentId) {
        log.info("Try to cancelReserveShipment id={}", shipmentId);
        return shipmentService.cancelReserveShipment(shipmentId);
    }

}
