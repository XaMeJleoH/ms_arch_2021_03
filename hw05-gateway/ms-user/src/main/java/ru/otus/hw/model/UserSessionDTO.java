package ru.otus.hw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_session", schema = "hw")
public class UserSessionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDTO userDTO;

    @Column(name = "session_id")
    private UUID sessionId;

    public UserSessionDTO(UserDTO userDTO, UUID sessionId) {
        this.userDTO = userDTO;
        this.sessionId = sessionId;
    }
}
