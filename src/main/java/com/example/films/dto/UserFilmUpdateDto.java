package com.example.films.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilmUpdateDto {

    private Long id;

    private Long filmId;

    private Long rating;
}
