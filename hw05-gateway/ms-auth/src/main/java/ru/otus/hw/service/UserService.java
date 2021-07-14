package ru.otus.hw.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.hw.mapper.UserMapping;
import ru.otus.hw.model.User;
import ru.otus.hw.model.UserDTO;
import ru.otus.hw.model.UserSessionDTO;
import ru.otus.hw.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(Integer userId) {
        UserDTO userDTO = userRepository.findById(userId).orElse(null);
        return UserMapping.mapper.userDTOToUser(userDTO);
    }

    public User createUser(User user) {
        Optional<UserDTO> userDTOOptional = userRepository.findByUsername(user.getUsername());
        if (userDTOOptional.isPresent()) {
            return UserMapping.mapper.userDTOToUser(userDTOOptional.get());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserDTO userDTO = userRepository.save(UserMapping.mapper.userToUserDTO(user));
        return UserMapping.mapper.userDTOToUser(userDTO);
    }

    public boolean updateUser(User user) {
        Optional<UserDTO> userDTOOptional = userRepository.findById(user.getId());
        if (userDTOOptional.isEmpty()) {
            return false;
        }
        UserDTO userDTO = userDTOOptional.get();
        if (StringUtils.isNotBlank(user.getLastName())) {
            userDTO.setLastName(user.getLastName());
        }
        if (StringUtils.isNotBlank(user.getFirstName())) {
            userDTO.setFirstName(user.getFirstName());
        }
        if (StringUtils.isNotBlank(user.getUsername())) {
            userDTO.setUsername(user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            userDTO.setEmail(user.getEmail());
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            userDTO.setPhone(user.getPhone());
        }
        userRepository.save(userDTO);
        return true;
    }

    public boolean deleteUser(Integer userId) {
        Optional<UserDTO> userDTOOptional = userRepository.findById(userId);
        if (userDTOOptional.isEmpty()) {
            return false;
        }
        userRepository.delete(userDTOOptional.get());
        return true;
    }

}
