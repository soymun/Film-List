package com.example.films.Controller;

import com.example.films.dto.LoginDTO;
import com.example.films.dto.RegDTO;
import com.example.films.facade.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/film")
@CrossOrigin(origins="http://localhost:3000")
public class AuthController {

    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public AuthController(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegDTO regDTO){
        return authenticationFacade.registration(regDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        return authenticationFacade.login(loginDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/registration/{id}")
    public ResponseEntity<?> registrationAdmin(@PathVariable Long id){
        return authenticationFacade.registrationAdmin(id);
    }

}
