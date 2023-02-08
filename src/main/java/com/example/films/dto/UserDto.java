package com.example.films.dto;

import com.example.films.entity.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private String surname;

    private Role role;
}
