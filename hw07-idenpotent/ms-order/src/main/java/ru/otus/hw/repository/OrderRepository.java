package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.model.OrderDTO;

@Repository
public interface OrderRepository extends CrudRepository<OrderDTO, Long> {
}
