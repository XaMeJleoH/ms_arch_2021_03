package ru.otus.hw.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.otus.hw.model.User;
import ru.otus.hw.model.UserDTO;

@Mapper
public interface UserMapping {
    public UserMapping mapper = Mappers.getMapper(UserMapping.class);

    User userDTOToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
}
