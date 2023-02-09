package com.example.films.Mappers;

import com.example.films.dto.GenreCreateDto;
import com.example.films.dto.GenreDto;
import com.example.films.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMappers {

    Genre genreCreateDtoToGenre(GenreCreateDto genreCreateDto);

    GenreDto genreToGenreDto(Genre genre);
}
