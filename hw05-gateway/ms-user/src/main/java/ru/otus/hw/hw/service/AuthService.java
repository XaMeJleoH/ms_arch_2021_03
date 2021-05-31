package ru.otus.hw.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.User;
import ru.otus.hw.model.UserDTO;
import ru.otus.hw.model.UserSessionDTO;
import ru.otus.hw.repository.UserRepository;
import ru.otus.hw.repository.UserSessionRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    public static final String SESSION_ID = "session_id";
    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<UUID, UserSessionDTO> SESSION_LIST = new HashMap<>();

    public ResponseEntity<String> login(User user, HttpServletResponse httpServletResponse) {
        Optional<UserDTO> userDTOOptional = userRepository.findByUsername(user.getUsername());
        if (userDTOOptional.isEmpty()) {
            return new ResponseEntity<String>("Такой клиент не существует", HttpStatus.UNAUTHORIZED);
        }
        if (passwordEncoder.matches(user.getPassword(), userDTOOptional.get().getPassword())) {
            log.info("Password is correct");
            UUID sessionId = UUID.randomUUID();
            UserSessionDTO userSessionDTO = new UserSessionDTO(userDTOOptional.get(), sessionId);
            SESSION_LIST.put(sessionId, userSessionDTO);
            userSessionRepository.save(userSessionDTO);
            setCookie(httpServletResponse, sessionId);
            return new ResponseEntity<String>("Hello World", HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Не удалось залогинитьсяя", HttpStatus.UNAUTHORIZED);
    }

    private void setCookie(HttpServletResponse response, UUID sessionId) {
        Cookie cookie = new Cookie("SESSION_ID", sessionId.toString());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public ResponseEntity<String> auth(Map<String, String> headers) {
        String sessionId = getSessionId(headers);
        if (StringUtils.isBlank(sessionId)) {
            return new ResponseEntity<String>("Не удалось определить сессию", HttpStatus.UNAUTHORIZED);
        }
        UUID sessionUUID = UUID.fromString(sessionId);
        UserSessionDTO userSessionDTO = SESSION_LIST.get(sessionUUID);
        if (userSessionDTO == null) {
            return new ResponseEntity<String>("Не удалось авторизоваться, не удалось найти нужную сессию", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<String>("Hello !!! World", HttpStatus.OK);
    }

    private String getSessionId(Map<String, String> headers) {
        String cookie = headers.get("cookie");
        if (StringUtils.isBlank(cookie)) {
            return cookie;
        }
        return cookie.replace(SESSION_ID.toUpperCase() + "=", "");
    }

    public ResponseEntity<String> logout(Map<String, String> headers, HttpServletResponse httpServletResponse) {
        String sessionId = getSessionId(headers);
        UserSessionDTO userSessionDTO = SESSION_LIST.get(UUID.fromString(sessionId));
        Cookie cookie = new Cookie("SESSION_ID", sessionId);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        userSessionRepository.delete(userSessionDTO);
        return new ResponseEntity<String>("Вы успешно вышли из системы", HttpStatus.OK);
    }
}
