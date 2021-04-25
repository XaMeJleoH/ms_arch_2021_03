package ru.otus.hw.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.otus.hw.mapper.UserMapping;
import ru.otus.hw.model.User;
import ru.otus.hw.model.UserDTO;
import ru.otus.hw.repository.UserRepository;

import java.util.Optional;

@Data
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getUser(Integer userId){
        UserDTO userDTO =  userRepository.findById(userId).orElse(null);
        return UserMapping.mapper.userDTOToUser(userDTO);
    }

    public User createUser(User user){
        UserDTO userDTO = userRepository.save(UserMapping.mapper.userToUserDTO(user));
        return UserMapping.mapper.userDTOToUser(userDTO);

    }

    public boolean updateUser(User user){
        Optional<UserDTO> userDTOOptional = userRepository.findById(user.getId());
        if(userDTOOptional.isEmpty()){
            return false;
        }
        userRepository.delete(userDTOOptional.get());
        return true;
    }

    public boolean deleteUser(Integer userId){
        Optional<UserDTO> userDTOOptional = userRepository.findById(userId);
        if(userDTOOptional.isEmpty()){
            return false;
        }
        userRepository.delete(userDTOOptional.get());
        return true;
    }
}
