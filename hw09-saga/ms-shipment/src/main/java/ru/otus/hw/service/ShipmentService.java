package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.Shipment;
import ru.otus.hw.model.ShipmentDTO;
import ru.otus.hw.repository.ShipmentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;

    public Long reserveShipment(Shipment shipment) {
        var shipmentDTO = shipmentRepository.save(createShipmentDTO(shipment));
        if (Boolean.FALSE.equals(shipment.getSuccessReserve())) {
            log.info("Reserve is failed={}", shipmentDTO);
            cancelReserveWarehouse();
            throw new RuntimeException("Резерв отправки не удался");
        }
        log.info("Reserve is success={}", shipmentDTO);
        return shipmentDTO.getId();
    }

    private void cancelReserveWarehouse() {
        log.info("Отменяем резерв на складе");
        //call cancel Reserve in Warehouse
    }

    public boolean cancelReserveShipment(Long orderId) {
        var shipmentDTO = shipmentRepository.findById(orderId);
        if (shipmentDTO.isEmpty()) {
            throw new RuntimeException("Ой, что-то пошло не так, не найден такой резерв отправки");
        }
        shipmentDTO.get().setCanceledReserve(true);
        shipmentRepository.save(shipmentDTO.get());
        log.info("Reserve is canceled={}", shipmentDTO);
        cancelReserveWarehouse();
        return true;
    }

    private ShipmentDTO createShipmentDTO(Shipment shipment) {
        ShipmentDTO shipmentDTO = new ShipmentDTO();
        shipmentDTO.setUserId(shipment.getUserId());
        shipmentDTO.setAddress(shipment.getAddress());
        shipmentDTO.setSuccess(shipment.getSuccessReserve());
        return shipmentDTO;
    }
}
