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

    private List<String> genre;
}
