package com.example.films.service.Impl;

import com.example.films.Exceptions.NoFindResource;
import com.example.films.Mappers.UserMapper;
import com.example.films.dto.UserDto;
import com.example.films.dto.UserUpdateDto;
import com.example.films.entity.User;
import com.example.films.repository.UserRepository;
import com.example.films.service.Inerface.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRole().authorities());
    }


    @Override
    public UserDto saveUser(User user) {
        log.info("Сохранение пользователя с email {}", user.getEmail());
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Выдача пользователя с id {}", id);
        return userMapper.userToUserDto(userRepository.findUserById(id));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Выдача пользователя с email {}", email);
        return userMapper.userToUserDto(userRepository.findUserByEmail(email));
    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto) {
        User user = userRepository.findUserById(userUpdateDto.getId());
        if(user == null){
            throw new NoFindResource("Пользователь не был найден");
        }
        if(userUpdateDto.getName() != null){
            user.setName(userUpdateDto.getName());
        }
        if(userUpdateDto.getSurname() != null){
            user.setSurname(userUpdateDto.getSurname());
        }
        if(userUpdateDto.getEmail() != null){
            userUpdateDto.setEmail(userUpdateDto.getEmail());
        }
        if(userUpdateDto.getPassword() != null){
            userUpdateDto.setPassword(userUpdateDto.getPassword());
        }
        if(userUpdateDto.getRole() != null){
            userUpdateDto.setRole(userUpdateDto.getRole());
        }
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
