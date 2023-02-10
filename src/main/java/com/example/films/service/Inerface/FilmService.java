package com.example.films.service.Inerface;

import com.example.films.dto.*;

import java.util.List;

public interface FilmService {

    void saveFilm(FilmCreateDto filmCreateDto);

    void updateFilm(FilmUpdateDto filmUpdateDto);

    FilmDTO getFilmById(Long id);

    List<FilmDTO> getFilmToUser(Long userId);

    List<FilmDTO> getFilmToRating(Long page);

    void deleteFilmById(Long id);

    void saveFilmGenre(FilmGenreCreateDto filmGenreCreateDto);

    void saveUserFilm(UserFilmCreateDto userFilmCreateDto);

    void updateUserFilm(UserFilmUpdateDto userFilmUpdateDto);

    void deleteUserFilmById(Long id);

    void deleteFilmGenreById(Long id);
}
