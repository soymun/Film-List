package com.example.films.Response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDto {

    private Object data;

    private String error;
}
