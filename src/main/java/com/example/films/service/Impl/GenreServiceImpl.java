package com.example.films.service.Impl;

import com.example.films.Exceptions.NoFindResource;
import com.example.films.Mappers.GenreMappers;
import com.example.films.dto.GenreCreateDto;
import com.example.films.dto.GenreDto;
import com.example.films.repository.GenreRepository;
import com.example.films.service.Inerface.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMappers genreMappers;

    @Override
    public GenreDto saveGenre(GenreCreateDto genreCreateDto) {
        log.info("Сохранение жанра {}", genreCreateDto.getGenre());
        return genreMappers.genreToGenreDto(genreRepository.save(genreMappers.genreCreateDtoToGenre(genreCreateDto)));
    }

    @Override
    public List<GenreDto> getAllGenre() {
        log.info("Выдача всех жанров");
        return genreRepository.findAll().stream().map(genreMappers::genreToGenreDto).collect(Collectors.toList());
    }

    @Override
    public GenreDto getGenreById(Long id) {
        log.info("Выдача жанра по id {}", id);
        return genreMappers.genreToGenreDto(genreRepository.getGenreById(id).orElseThrow(() -> {throw new NoFindResource("Такого жанра не существует");}));
    }

    @Override
    public void deleteGenre(Long id) {
        log.info("Удаление жанра id {}", id);
        genreRepository.deleteById(id);
    }
}
