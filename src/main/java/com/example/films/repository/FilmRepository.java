package com.example.films.repository;

import com.example.films.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Override
    void deleteById(Long aLong);

    Film findFilmById(Long id);
}
