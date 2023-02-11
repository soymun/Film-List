package com.example.films.Exceptions.ExceptionHandlers;

import com.example.films.Exceptions.NoFindResource;
import com.example.films.Response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoFindResource.class)
    public ResponseEntity<?> noFind(NoFindResource exc){
        log.debug(Arrays.toString(exc.getStackTrace()));
        return ResponseEntity.ok(ResponseDto.builder().error(exc.getMessage()).build());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(NoFindResource exc){
        log.debug(Arrays.toString(exc.getStackTrace()));
        return ResponseEntity.ok(ResponseDto.builder().error("Упс, что то случилось, попробуйте позже"));
    }
}
