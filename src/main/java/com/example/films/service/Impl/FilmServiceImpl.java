package com.example.films.service.Impl;

import com.example.films.Exceptions.NoFindResource;
import com.example.films.Mappers.FilmMapper;
import com.example.films.dto.*;
import com.example.films.entity.*;
import com.example.films.entity.Comment_;
import com.example.films.entity.FilmGenre_;
import com.example.films.entity.Film_;
import com.example.films.entity.Genre_;
import com.example.films.entity.UserFilm_;
import com.example.films.repository.FilmGenreRepository;
import com.example.films.repository.FilmRepository;
import com.example.films.repository.UserFilmRepository;
import com.example.films.service.Inerface.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final FilmGenreRepository filmGenreRepository;

    private final UserFilmRepository userFilmRepository;

    private final FilmMapper filmMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void saveFilm(FilmCreateDto filmCreateDto) {
        log.info("Сохранение фильма {}", filmCreateDto.getName());
        FilmDTO filmDTO = filmMapper.filmToFilmCreateDto(filmRepository.save(filmMapper.filmCreateDtoToFilm(filmCreateDto)));
        filmCreateDto.getGenreId().stream().map(n -> new FilmGenre(filmDTO.getId(), n)).forEach(filmGenreRepository::save);
    }

    @Override
    @Transactional
    public void updateFilm(FilmUpdateDto filmUpdateDto) {
        log.info("Изменение фильма с id {}", filmUpdateDto.getId());
       Film film = filmRepository.getFilmById(filmUpdateDto.getId()).orElseThrow(() -> {throw new NoFindResource("Фильм не найлен");});
       if(filmUpdateDto.getDescription() != null){
           film.setDescription(filmUpdateDto.getDescription());
       }
       if(filmUpdateDto.getName() != null){
            film.setName(filmUpdateDto.getName());
       }
       if(filmUpdateDto.getUrl() != null){
            film.setUrl(filmUpdateDto.getUrl());
       }
       if(filmUpdateDto.getYear() != null){
            film.setYear(filmUpdateDto.getYear());
       }
       filmRepository.save(film);
    }

    @Override
    public FilmDTO getFilmById(Long id) {
        log.info("Выдача фильма по id {}", id);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FilmDTO> cq = cb.createQuery(FilmDTO.class);
        Root<Film> root = cq.from(Film.class);

        //Find grade
        Subquery<Double> subQuery = cq.subquery(Double.class);
        Root<Comment> root1 = subQuery.from(Comment.class);
        subQuery.where(cb.equal(root1.get(Comment_.filmId), id));
        subQuery.select(cb.avg(root1.get(Comment_.grade)));

        Subquery<String> subQuery1 = cq.subquery(String.class);
        Root<FilmGenre> sRoot = subQuery1.from(FilmGenre.class);
        Join<FilmGenre, Genre> join2 = sRoot.join(FilmGenre_.GENRE);
        subQuery1.where(cb.equal(sRoot.get(FilmGenre_.filmId), id));
        subQuery1.select(join2.get(Genre_.genre));

        cq.where(cb.equal(root.get(Film_.id), id));

        //select
        cq.multiselect(
                root.get(Film_.id),
                root.get(Film_.name),
                root.get(Film_.description),
                root.get(Film_.url),
                root.get(Film_.year),
                cb.coalesce(subQuery,cb.literal(3.0))
        );
        FilmDTO filmDTO = entityManager.createQuery(cq).getSingleResult();
        filmDTO.setGenreDtos(getGenreByFilmId(filmDTO.getId()));
        return filmDTO;
    }

    @Override
    public List<FilmDTO> getFilmToUser(Long userId) {
        log.info("Выдача фильма пользователя с id {}", userId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FilmDTO> cq = cb.createQuery(FilmDTO.class);
        Root<UserFilm> root = cq.from(UserFilm.class);
        Join<UserFilm, Film> join = root.join(UserFilm_.FILM);

        //Find grade
        Subquery<Double> subQuery = cq.subquery(Double.class);
        Root<Comment> root1 = subQuery.from(Comment.class);
        subQuery.where(cb.equal(root1.get(Comment_.filmId), join.get(Film_.id)));
        subQuery.select(cb.coalesce(cb.avg(root1.get(Comment_.grade)), 0.0));

        cq.where(cb.equal(root.get(UserFilm_.userId), userId));
        cq.orderBy(cb.asc(root.get(UserFilm_.placeInRatingUser)));

        //select
        cq.multiselect(
                join.get(Film_.id),
                join.get(Film_.name),
                join.get(Film_.description),
                join.get(Film_.url),
                join.get(Film_.year),
                cb.coalesce(subQuery,cb.literal(3.0))
        );
        return entityManager
                .createQuery(cq)
                .getResultList().stream().peek(n -> n.setGenreDtos(getGenreByFilmId(n.getId()))).collect(Collectors.toList());
    }

    @Override
    public List<FilmDTO> getFilmToRating(Long page) {
        log.info("Выдача фильмов");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FilmDTO> cq = cb.createQuery(FilmDTO.class);
        Root<Film> root = cq.from(Film.class);

        //Find grade
        Subquery<Double> subQuery = cq.subquery(Double.class);
        Root<Comment> root1 = subQuery.from(Comment.class);
        subQuery.where(cb.equal(root1.get(Comment_.filmId), root.get(Film_.id)));
        subQuery.select(cb.avg(root1.get(Comment_.grade)));

        //select
        cq.multiselect(
                root.get(Film_.id),
                root.get(Film_.name),
                root.get(Film_.description),
                root.get(Film_.url),
                root.get(Film_.year),
                cb.coalesce(subQuery,cb.literal(3.0))
        );
        return entityManager.createQuery(cq)
                .setFirstResult((int) ((page-1)*30))
                .setMaxResults((int) (page * 30))
                .getResultList().stream()
                .sorted((o1, o2) -> (int) (o1.getAvgGrade()-o2.getAvgGrade()))
                .peek(n -> n.setGenreDtos(getGenreByFilmId(n.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteFilmById(Long id) {
        log.info("Удаление фильма с id {}", id);
        userFilmRepository.deleteByFilmId(id);
        filmRepository.deleteById(id);
    }

    @Override
    public void saveFilmGenre(FilmGenreCreateDto filmGenreCreateDto) {
        log.info("Сохранение жанров фильма с id {}", filmGenreCreateDto.getFilmId());
        filmGenreRepository.save(new FilmGenre(filmGenreCreateDto.getFilmId(), filmGenreCreateDto.getGenreId()));
    }

    @Override
    public void saveUserFilm(UserFilmCreateDto userFilmCreateDto) {
        log.info("Сохранение фильма пользователя с id {}", userFilmCreateDto.getUserId());
        userFilmRepository.save(new UserFilm(userFilmCreateDto.getUserId(), userFilmCreateDto.getFilmId(), userFilmCreateDto.getRating()));
    }

    @Override
    public void updateUserFilm(UserFilmUpdateDto userFilmUpdateDto) {
        log.info("Изменение фильма пользователя с id {}", userFilmUpdateDto.getId());
        UserFilm userFilm = userFilmRepository.findById(userFilmUpdateDto.getId()).orElseThrow(()-> {throw new NoFindResource("Фильм пользователя не найден");});
        if(userFilmUpdateDto.getFilmId() != null){
            userFilm.setFilmId(userFilm.getFilmId());
        }
        if(userFilmUpdateDto.getRating() != null){
            userFilm.setPlaceInRatingUser(userFilmUpdateDto.getRating());
        }
        userFilmRepository.save(userFilm);
    }

    @Override
    public void deleteUserFilmById(Long id) {
        log.info("Удаление фильма пользователя с id {}", id);
        userFilmRepository.deleteById(id);
    }

    @Override
    public void deleteFilmGenreById(Long id) {
        log.info("Удаление жанра фильма с id {}", id);
        filmGenreRepository.deleteById(id);
    }

    @Override
    public List<GenreDto> getGenreByFilmId(Long filmId) {
        log.info("Выдача жанров фильма с id {}", filmId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GenreDto> cq = cb.createQuery(GenreDto.class);
        Root<FilmGenre> root = cq.from(FilmGenre.class);

        Join<FilmGenre, Genre> join = root.join(FilmGenre_.GENRE);

        cq.where(cb.equal(root.get(FilmGenre_.filmId), filmId));

        cq.multiselect(
                join.get(Genre_.id),
                join.get(Genre_.genre)
        );
        return entityManager.createQuery(cq).getResultList();
    }
}
