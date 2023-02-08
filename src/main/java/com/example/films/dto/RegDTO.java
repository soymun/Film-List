package com.example.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegDTO {

    @JsonProperty(required = true)
    private String email;

    @JsonProperty(required = true)
    private String password;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private String surname;
}
