package com.example.films.Controller;


import com.example.films.dto.FilmDTO;
import com.example.films.entity.Film;
import com.example.films.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hoq")
public class ContentController {
//    private UserService userService;
//    @Autowired
//    public ContentController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/films")
//    public List<Film> getFilm(@RequestBody Long id){
//        return userService.getUserById(id).getFilm();
//    }
//
//    @PostMapping("/film/create/{id}")
//    public ResponseEntity<?> addFilm(@RequestBody FilmDTO filmDTO, @PathVariable Long id){
//        Film film = new Film();
//        if(filmDTO.getName() != null){
//            film.setName(filmDTO.getName());
//        }
//        if(filmDTO.getUrl() != null){
//            film.setUrl(filmDTO.getName());
//        }
//        film.setUserId(id);
//        userService.saveFilmAndUpdate(film);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PutMapping("/film/update")
//    public ResponseEntity<?> updateFilm(@RequestBody FilmDTO filmDTO){
//        Film film = userService.getFilm(filmDTO.getId());
//        if(film == null){
//            throw new RuntimeException("Film not found");
//        }
//        if(filmDTO.getName() != null){
//            film.setName(filmDTO.getName());
//        }
//        if(filmDTO.getUrl() != null){
//            film.setUrl(filmDTO.getName());
//        }
//        userService.saveFilmAndUpdate(film);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/film/delete/{id}")
//    public void deleteFilm(@PathVariable Long id){
//        userService.deleteFilm(id);
//    }
}
