package com.example.films.repository;

import com.example.films.entity.UserFilm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFilmRepository extends JpaRepository<UserFilm, Long> {

    void deleteByFilmId(Long id);

}
