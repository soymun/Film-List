package com.example.films.Controller;

import com.example.films.Response.ResponseDto;
import com.example.films.dto.GenreCreateDto;
import com.example.films.service.Inerface.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/film/genres")
@CrossOrigin(origins="http://localhost:3000")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<?> saveGenre(@RequestBody GenreCreateDto genreCreateDto) {
        return ResponseEntity.ok(ResponseDto.builder().data(genreService.saveGenre(genreCreateDto)).build());
    }

    @GetMapping
    public ResponseEntity<?> getAllGenre() {
        return ResponseEntity.ok(ResponseDto.builder().data(genreService.getAllGenre()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.builder().data(genreService.getGenreById(id)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
