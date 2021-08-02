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
            throw new RuntimeException("Резерв не удался");
        }
        log.info("Reserve is success={}", shipmentDTO);
        return shipmentDTO.getId();
    }

    public boolean cancelReserveShipment(Long orderId) {
        var shipmentDTO = shipmentRepository.findById(orderId);
        if (shipmentDTO.isEmpty()) {
            throw new RuntimeException("Ой, что-то пошло не так, не найден такой резерв");
        }
        shipmentDTO.get().setCanceledReserve(true);
        shipmentRepository.save(shipmentDTO.get());
        log.info("Reserve is canceled={}", shipmentDTO);
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
