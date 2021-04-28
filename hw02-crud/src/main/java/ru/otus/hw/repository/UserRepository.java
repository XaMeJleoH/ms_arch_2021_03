package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.model.UserDTO;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, Integer> {
}
