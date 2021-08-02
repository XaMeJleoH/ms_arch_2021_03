package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.model.PaymentDTO;

import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentDTO, Long> {
}
