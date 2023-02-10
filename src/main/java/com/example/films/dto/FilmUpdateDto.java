package com.example.films.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmUpdateDto {

    private Long id;

    private String name;

    private String description;

    private String url;

    private LocalDate year;
}
