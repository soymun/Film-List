package com.example.films.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {

    private Long id;

    private String name;

    private String description;

    private String url;

    private LocalDate year;

    private double avgGrade;

    private List<GenreDto> genreDtos;

    public FilmDTO(Long id, String name, String description, String url, LocalDate year, double avgGrade) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.year = year;
        this.avgGrade = avgGrade;
    }
}
