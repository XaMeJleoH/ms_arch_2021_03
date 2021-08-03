package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.hw.model.Shipment;
import ru.otus.hw.model.ShipmentDTO;
import ru.otus.hw.model.ShipmentStatus;
import ru.otus.hw.repository.ShipmentRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentService {
    private static final String URL_WAREHOUSE_CANCEL_RESERVE = "http://localhost:83/warehouse/";

    private final ShipmentRepository shipmentRepository;

    public Long reserveShipment(Shipment shipment) {
        var shipmentDTO = shipmentRepository.save(createShipmentDTO(shipment));
        if (Boolean.FALSE.equals(shipment.getSuccessReserve())) {
            log.info("Reserve is failed={}", shipmentDTO);
            shipmentDTO.setShipmentStatus(ShipmentStatus.FAILED);
            shipmentRepository.save(shipmentDTO);
            cancelReserveWarehouse(shipment.getReserveWarehouseId());
            throw new RuntimeException("Резерв отправки не удался");
        }
        shipmentDTO.setShipmentStatus(ShipmentStatus.FINISH);
        shipmentRepository.save(shipmentDTO);
        log.info("Reserve is success={}", shipmentDTO);
        return shipmentDTO.getId();
    }

    private void cancelReserveWarehouse(Long reserveWarehouseId) {
        log.info("Отменяем резерв на складе");
        //call cancel Reserve in Warehouse
        ResponseEntity<String> response = callService(reserveWarehouseId);
        log.info("Result call={}", response);
    }

    private ResponseEntity<String> callService(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_WAREHOUSE_CANCEL_RESERVE)
                .path(id.toString());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(
                builder.toUriString(),
                entity,
                String.class);
    }

    public boolean cancelReserveShipment(Long shipmentId) {
        ShipmentDTO shipmentDTO = getShipmentDTO(shipmentId);
        shipmentDTO.setShipmentStatus(ShipmentStatus.CANCELED);
        shipmentRepository.save(shipmentDTO);
        log.info("Reserve is canceled={}", shipmentDTO);
        cancelReserveWarehouse(shipmentDTO.getReserveWarehouseId());
        return true;
    }

    private ShipmentDTO createShipmentDTO(Shipment shipment) {
        ShipmentDTO shipmentDTO = new ShipmentDTO();
        shipmentDTO.setUserId(shipment.getUserId());
        shipmentDTO.setReserveWarehouseId(shipment.getReserveWarehouseId());
        shipmentDTO.setAddress(shipment.getAddress());
        shipmentDTO.setSuccess(shipment.getSuccessReserve());
        return shipmentDTO;
    }

    public ShipmentStatus getStatus(Long shipmentId) {
        ShipmentDTO shipmentDTO = getShipmentDTO(shipmentId);
        return shipmentDTO.getShipmentStatus();
    }

    private ShipmentDTO getShipmentDTO(Long shipmentId) {
        var shipmentDTO = shipmentRepository.findById(shipmentId);
        if (shipmentDTO.isEmpty()) {
            throw new RuntimeException("Ой, что-то пошло не так, не найден такой резерв отправки");
        }
        return shipmentDTO.get();
    }
}
