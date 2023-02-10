package com.example.films.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilmCreateDto {

    private Long userId;

    private Long filmId;

    private Long rating;
}
