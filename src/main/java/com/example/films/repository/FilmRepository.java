package com.example.films.repository;

import com.example.films.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {

    Optional<Film> getFilmById(Long id);
}
