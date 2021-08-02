package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.hw.model.Warehouse;
import ru.otus.hw.model.WarehouseDTO;
import ru.otus.hw.repository.WarehouseRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseService {
    private static final String URL_CANCEL_PAYMENT = "http://localhost:81/payment/";

    private final WarehouseRepository warehouseRepository;

    public Long reserve(Warehouse warehouse) {
        var warehouseDTO = warehouseRepository.save(createWarehouseDTO(warehouse));
        if (Boolean.FALSE.equals(warehouse.getSuccessReserve())) {
            log.info("Reserve is failed={}", warehouseDTO);
            cancelPayment(warehouse.getPaymentId());
            throw new RuntimeException("Резерв не удался");
        }
        log.info("Reserve is success={}", warehouseDTO);
        return warehouseDTO.getId();
    }

    private void cancelPayment(Long paymentId) {
        log.info("Отменяем оплату");
        //call cancel Payment
        ResponseEntity<String> response = callService(paymentId);
        log.info("Result call={}", response);
    }

    private ResponseEntity<String> callService(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_CANCEL_PAYMENT)
                .path(id.toString());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(
                builder.toUriString(),
                entity,
                String.class);
    }

    public boolean cancelReserve(Long orderId) {
        var warehouseDTO = warehouseRepository.findById(orderId);
        if (warehouseDTO.isEmpty()) {
            throw new RuntimeException("Ой, что-то пошло не так, не найдена такой резерв");
        }
        warehouseDTO.get().setCanceledReserve(true);
        warehouseRepository.save(warehouseDTO.get());
        log.info("Reserve is canceled={}", warehouseDTO);
        cancelPayment(warehouseDTO.get().getPaymentId());
        return true;
    }

    private WarehouseDTO createWarehouseDTO(Warehouse warehouse) {
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        warehouseDTO.setUserId(warehouse.getUserId());
        warehouseDTO.setPaymentId(warehouse.getPaymentId());
        warehouseDTO.setOrderName(warehouse.getOrderName());
        warehouseDTO.setSuccess(warehouse.getSuccessReserve());
        return warehouseDTO;
    }
}
