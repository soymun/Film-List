package com.example.films.F;

import com.example.films.dto.RegDTO;
import com.example.films.entity.Role;
import com.example.films.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class FUser {

    public static User createUser(RegDTO regDTO){
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        User user = new User();
        user.setEmail(regDTO.getEmail());
        user.setPassword(bc.encode(regDTO.getPassword()));
        user.setName(regDTO.getName());
        user.setSurname(regDTO.getSurname());
        user.setRole(List.of(Role.USER));
        return user;
    }
}
