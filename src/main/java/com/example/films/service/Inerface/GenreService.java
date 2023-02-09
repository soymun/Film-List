package com.example.films.service.Inerface;

import com.example.films.dto.GenreCreateDto;
import com.example.films.dto.GenreDto;

import java.util.List;

public interface GenreService {

    GenreDto saveGenre(GenreCreateDto genreCreateDto);

    List<GenreDto> getAllGenre();

    GenreDto getGenreById(Long id);

    void deleteGenre(Long id);
}
