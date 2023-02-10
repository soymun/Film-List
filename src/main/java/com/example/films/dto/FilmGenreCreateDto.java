package com.example.films.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmGenreCreateDto {

    private Long filmId;

    private Long genreId;
}
