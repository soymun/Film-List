package com.example.films.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmCreateDto {

    private String name;

    private String description;

    private String url;

    private LocalDate year;

    private List<Long> genreId;
}
