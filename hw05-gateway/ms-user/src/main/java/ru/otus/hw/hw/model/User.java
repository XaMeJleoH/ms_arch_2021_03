package ru.otus.hw.hw.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

}
