package com.example.films.Controller;


import com.example.films.Response.ResponseDto;
import com.example.films.dto.*;
import com.example.films.service.Impl.FilmServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
public class ContentController {

    private final FilmServiceImpl filmService;

    @PostMapping("/film")
    public ResponseEntity<?> saveFilm(@RequestBody FilmCreateDto filmCreateDto){
        filmService.saveFilm(filmCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/film")
    public ResponseEntity<?> updateFilm(@RequestBody FilmUpdateDto filmUpdateDto){
        filmService.updateFilm(filmUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/film/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().data(filmService.getFilmById(id)).build());
    }

    @GetMapping("/film/user/{id}")
    public ResponseEntity<?> getFilmByUserId(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().data(filmService.getFilmToUser(id)).build());
    }

    @GetMapping("/film")
    public ResponseEntity<?> getFilmByRating(@RequestParam Long page){
        return ResponseEntity.ok(ResponseDto.builder().data(filmService.getFilmToRating(page)).build());
    }

    @DeleteMapping("/film/{id}")
    public ResponseEntity<?> deleteFilmById(@PathVariable Long id){
        filmService.deleteFilmById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/film/genre")
    public ResponseEntity<?> saveFilmGenre(@RequestBody FilmGenreCreateDto filmGenreCreateDto){
        filmService.saveFilmGenre(filmGenreCreateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/film/user")
    public ResponseEntity<?> saveUserFilm(@RequestBody UserFilmCreateDto userFilmCreateDto){
        filmService.saveUserFilm(userFilmCreateDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/film/user")
    public ResponseEntity<?> updateUserFilm(@RequestBody UserFilmUpdateDto userFilmCreateDto){
        filmService.updateUserFilm(userFilmCreateDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/film/user/{id}")
    public ResponseEntity<?> deleteFilmUserById(@PathVariable Long id){
        filmService.deleteUserFilmById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/film/genre/{id}")
    public ResponseEntity<?> deleteFilmGenreById(@PathVariable Long id){
        filmService.deleteFilmGenreById(id);
        return ResponseEntity.noContent().build();
    }
}
