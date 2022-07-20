package com.example.films.Controller;


import com.example.films.F.FUser;
import com.example.films.dto.LoginDTO;
import com.example.films.dto.RegDTO;
import com.example.films.entity.User;
import com.example.films.jwt.JwtTokenProvider;
import com.example.films.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AuthController {

    private UserService userService;

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegDTO regDTO){
        User userEx = userService.getUserByEmail(regDTO.getEmail());
        if(userEx != null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        User user = FUser.createUser(regDTO);
        userService.save(user);
        return ResponseEntity.ok("Suggest");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            User user = userService.getUserByEmail(loginDTO.getEmail());
            String token = jwtTokenProvider.create(user.getEmail(), user.getRole().get(0));
            Map<Object, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("email", user.getEmail());
            map.put("token", token);
            return ResponseEntity.ok(map);
        }catch (AuthenticationException e){
            throw new RuntimeException("User not found");
        }
    }

    @PostMapping("/logt")
    public ResponseEntity<?> logout(HttpServletResponse response, HttpServletRequest httpServletRequest){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(httpServletRequest, response, null);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
