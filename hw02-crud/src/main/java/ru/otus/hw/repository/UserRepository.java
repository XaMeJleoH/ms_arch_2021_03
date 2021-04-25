package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.model.UserDTO;

public interface UserRepository extends CrudRepository<UserDTO, Integer> {
}
