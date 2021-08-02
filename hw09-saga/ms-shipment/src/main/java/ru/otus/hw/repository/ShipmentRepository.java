package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.model.ShipmentDTO;

@Repository
public interface ShipmentRepository extends CrudRepository<ShipmentDTO, Long> {
}
