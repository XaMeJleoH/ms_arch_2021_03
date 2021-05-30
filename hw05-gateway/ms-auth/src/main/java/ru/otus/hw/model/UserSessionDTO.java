package ru.otus.hw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_session", schema = "hw")
public class UserSessionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(mappedBy = "userSessionDTO")
    private UserDTO userDTO;

    @Column(name = "cookie")
    private String cookie;
}
