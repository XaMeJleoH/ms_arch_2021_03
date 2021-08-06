package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.model.WarehouseDTO;

@Repository
public interface WarehouseRepository extends CrudRepository<WarehouseDTO, Long> {
}
