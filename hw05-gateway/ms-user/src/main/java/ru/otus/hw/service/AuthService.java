package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.UserDTO;
import ru.otus.hw.model.UserSessionDTO;
import ru.otus.hw.repository.UserRepository;
import ru.otus.hw.repository.UserSessionRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private static final String SESSION_ID = "session_id";

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;

    public UserSessionDTO authorized(int userId) {
        Optional<UserDTO> userDTOOptional = userRepository.findById(userId);
        if (userDTOOptional.isEmpty()) {
            return null;
        }
        Optional<UserSessionDTO> userSessionDTO = userSessionRepository.findByUserDTO(userDTOOptional.get());
        return userSessionDTO.orElse(null);
    }

    public boolean isCorrectUser(int userId, Map<String, String> headers) {
        UserSessionDTO userSessionDTO = authorized(userId);
        if (userSessionDTO == null) {
            return false;
        }

        String sessionId = getSessionId(headers);
        if (StringUtils.isBlank(sessionId)){
            return false;
        }

        return userSessionDTO.getSessionId().equals(UUID.fromString(sessionId));
    }

    private String getSessionId(Map<String, String> headers) {
        String cookie = headers.get("cookie");
        if (StringUtils.isBlank(cookie)) {
            return null;
        }
        return cookie.replace(SESSION_ID.toUpperCase() + "=", "");
    }
}
