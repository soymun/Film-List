package com.example.films.repository;

import com.example.films.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

    User findUserById(Long id);
}
