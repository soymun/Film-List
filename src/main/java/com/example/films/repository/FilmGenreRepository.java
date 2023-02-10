package com.example.films.repository;

import com.example.films.entity.FilmGenre;
import com.example.films.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmGenreRepository extends JpaRepository<FilmGenre, Long> {

}
