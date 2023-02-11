package com.example.films.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate year;

    private List<Long> genreId;
}
