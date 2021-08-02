package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.Warehouse;
import ru.otus.hw.model.WarehouseDTO;
import ru.otus.hw.repository.WarehouseRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public Long reserve(Warehouse warehouse) {
        var warehouseDTO = warehouseRepository.save(createWarehouseDTO(warehouse));
        if (Boolean.FALSE.equals(warehouse.getSuccessReserve())) {
            log.info("Reserve is failed={}", warehouseDTO);
            throw new RuntimeException("Резерв не удался");
        }
        log.info("Reserve is success={}", warehouseDTO);
        return warehouseDTO.getId();
    }

    public boolean cancelReserve(Long orderId) {
        var warehouseDTO = warehouseRepository.findById(orderId);
        if (warehouseDTO.isEmpty()) {
            throw new RuntimeException("Ой, что-то пошло не так, не найдена такой резерв");
        }
        warehouseDTO.get().setCanceledReserve(true);
        warehouseRepository.save(warehouseDTO.get());
        log.info("Reserve is canceled={}", warehouseDTO);
        return true;
    }

    private WarehouseDTO createWarehouseDTO(Warehouse warehouse) {
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        warehouseDTO.setUserId(warehouse.getUserId());
        warehouseDTO.setOrderName(warehouse.getOrderName());
        warehouseDTO.setSuccess(warehouse.getSuccessReserve());
        return warehouseDTO;
    }
}
