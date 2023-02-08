package com.example.films.service.Inerface;

import com.example.films.dto.UserDto;
import com.example.films.dto.UserUpdateDto;
import com.example.films.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto saveUser(User user);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    UserDto updateUser(UserUpdateDto userUpdateDto);

    void deleteUserById(Long id);

}
