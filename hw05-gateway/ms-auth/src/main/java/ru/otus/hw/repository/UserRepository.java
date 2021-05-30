package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.model.UserDTO;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, Integer> {
    Optional<UserDTO> findByUsername(String userName);
}
