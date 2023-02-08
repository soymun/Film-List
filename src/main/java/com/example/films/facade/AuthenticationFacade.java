package com.example.films.facade;

import com.example.films.Exceptions.NoFindResource;
import com.example.films.Response.ResponseDto;
import com.example.films.dto.LoginDTO;
import com.example.films.dto.RegDTO;
import com.example.films.dto.UserDto;
import com.example.films.entity.Role;
import com.example.films.entity.User;
import com.example.films.jwt.JwtTokenProvider;
import com.example.films.service.Impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthenticationFacade {

    private final UserServiceImpl userServiceImpl;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationFacade(UserServiceImpl userServiceImpl, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<?> registration(@RequestBody RegDTO regDTO){
        UserDto findUser = userServiceImpl.getUserByEmail(regDTO.getEmail());
        if(findUser != null){
            log.info("Повторная регистрация с email {}", regDTO.getEmail());
            throw new RuntimeException("Пользователь с таким email уже зарегистрирован");
        }
        log.info("Регистрация пользователя с email {}", regDTO.getEmail());
        User user = new User();
        user.setRole(Role.USER);
        user.setEmail(regDTO.getEmail());
        user.setName(regDTO.getName());
        user.setSurname(regDTO.getSurname());
        user.setPassword(passwordEncoder.encode(regDTO.getPassword()));
        userServiceImpl.saveUser(user);
        return ResponseEntity.ok(ResponseDto.builder().data("Регистрация прошла успешна").build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        try {
            UserDto user = userServiceImpl.getUserByEmail(loginDTO.getEmail());
            if(user == null){
                throw new NoFindResource("Такого пользователя не существует");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            String token = jwtTokenProvider.create(user.getEmail(), user.getRole());
            Map<Object, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("email", user.getEmail());
            map.put("token", token);
            return ResponseEntity.ok(ResponseDto.builder().data(map).build());
        }catch (AuthenticationException e){
            throw new RuntimeException("Произошла ошибка. Попробуйте позже");
        }
    }

    public ResponseEntity<?> registrationAdmin(Long id){
        return null;
    }
}
