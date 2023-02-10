package com.example.films.Mappers;

import com.example.films.dto.FilmCreateDto;
import com.example.films.dto.FilmDTO;
import com.example.films.entity.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    Film filmCreateDtoToFilm(FilmCreateDto filmCreateDto);

    FilmDTO filmToFilmCreateDto(Film film);
}
