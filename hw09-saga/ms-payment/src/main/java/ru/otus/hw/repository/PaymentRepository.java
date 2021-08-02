package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.model.PaymentDTO;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentDTO, Long> {
}
