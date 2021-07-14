package ru.otus.hw.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.hw.mapper.UserMapping;
import ru.otus.hw.model.User;
import ru.otus.hw.model.UserDTO;
import ru.otus.hw.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public User getUser(Integer userId, Map<String, String> headers) {
        checkUser(userId, headers);
        UserDTO userDTO = userRepository.findById(userId).orElse(null);
        return UserMapping.mapper.userDTOToUser(userDTO);
    }

    private void checkUser(Integer userId, Map<String, String> headers) {
        if (!authService.isCorrectUser(userId, headers)){
            throw new RuntimeException("Клиент не авторизован или запрашивает не свою информацию");
        }
    }

    public boolean updateUser(User user, Map<String, String> headers) {
        checkUser(user.getId(), headers);
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

    public boolean deleteUser(Integer userId, Map<String, String> headers) {
        checkUser(userId, headers);
        Optional<UserDTO> userDTOOptional = userRepository.findById(userId);
        if (userDTOOptional.isEmpty()) {
            return false;
        }
        userRepository.delete(userDTOOptional.get());
        return true;
    }

}
