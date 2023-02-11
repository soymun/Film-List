package com.example.films.Controller;

import com.example.films.Response.ResponseDto;
import com.example.films.dto.UserUpdateDto;
import com.example.films.service.Inerface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/film/users")
@CrossOrigin(origins="http://localhost:3000")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.builder().data(userService.getUserById(id)).build());
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto) {

        return ResponseEntity.ok(ResponseDto.builder().data(userService.updateUser(userUpdateDto)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
