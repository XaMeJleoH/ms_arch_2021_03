package ru.otus.hw.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.model.UserDTO;
import ru.otus.hw.model.UserSessionDTO;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSessionDTO, Integer> {
    Optional<UserSessionDTO> findByUserDTO(UserDTO userDTO);
}
