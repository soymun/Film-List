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
        FilmDTO filmDTO = filmMapper.filmToFilmCreateDto(filmRepository.save(filmMapper.filmCreateDtoToFilm(filmCreateDto)));
        filmCreateDto.getGenreId().stream().map(n -> new FilmGenre(filmDTO.getId(), n)).forEach(filmGenreRepository::save);
    }

    @Override
    @Transactional
    public void updateFilm(FilmUpdateDto filmUpdateDto) {
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FilmDTO> cq = cb.createQuery(FilmDTO.class);
        Root<Comment> root = cq.from(Comment.class);
        Join<Comment, Film> join = root.join(Comment_.FILM);

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

        cq.where(cb.equal(join.get(Film_.id), id));

        //select
        cq.multiselect(
                join.get(Film_.id),
                join.get(Film_.name),
                join.get(Film_.description),
                join.get(Film_.url),
                join.get(Film_.year),
                subQuery,
                subQuery1
        );
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public List<FilmDTO> getFilmToUser(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FilmDTO> cq = cb.createQuery(FilmDTO.class);
        Root<Comment> root = cq.from(Comment.class);
        Join<Comment, Film> join = root.join(Comment_.FILM);
        Join<Film, UserFilm> join1 = join.join(Film_.ID);
        //Find grade
        Subquery<Double> subQuery = cq.subquery(Double.class);
        Root<Comment> root1 = subQuery.from(Comment.class);
        subQuery.where(cb.equal(root1.get(Comment_.filmId), join.get(Film_.id)));
        subQuery.select(cb.avg(root1.get(Comment_.grade)));

        Subquery<String> subQuery1 = cq.subquery(String.class);
        Root<FilmGenre> sRoot = subQuery1.from(FilmGenre.class);
        Join<FilmGenre, Genre> join2 = sRoot.join(FilmGenre_.GENRE);
        subQuery1.where(cb.equal(sRoot.get(FilmGenre_.filmId), join.get(Film_.id)));
        subQuery1.select(join2.get(Genre_.genre));

        cq.where(cb.equal(join1.get(UserFilm_.userId), userId));
        cq.orderBy(cb.asc(join1.get(UserFilm_.placeInRatingUser)));

        //select
        cq.multiselect(
                join.get(Film_.id),
                join.get(Film_.name),
                join.get(Film_.description),
                join.get(Film_.url),
                join.get(Film_.year),
                subQuery,
                subQuery1
        );
        return entityManager
                .createQuery(cq)
                .getResultList();
    }

    @Override
    public List<FilmDTO> getFilmToRating(Long page) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FilmDTO> cq = cb.createQuery(FilmDTO.class);
        Root<Comment> root = cq.from(Comment.class);
        Join<Comment, Film> join = root.join(Comment_.FILM);

        //Find grade
        Subquery<Double> subQuery = cq.subquery(Double.class);
        Root<Comment> root1 = subQuery.from(Comment.class);
        subQuery.where(cb.equal(root1.get(Comment_.filmId), join.get(Film_.id)));
        subQuery.select(cb.avg(root1.get(Comment_.grade)));

        Subquery<String> subQuery1 = cq.subquery(String.class);
        Root<FilmGenre> sRoot = subQuery1.from(FilmGenre.class);
        Join<FilmGenre, Genre> join2 = sRoot.join(FilmGenre_.GENRE);
        subQuery1.where(cb.equal(sRoot.get(FilmGenre_.filmId), join.get(Film_.id)));
        subQuery1.select(join2.get(Genre_.genre));

        cq.orderBy(cb.asc(subQuery));

        //select
        cq.multiselect(
                join.get(Film_.id),
                join.get(Film_.name),
                join.get(Film_.description),
                join.get(Film_.url),
                join.get(Film_.year),
                subQuery,
                subQuery1
        );
        return entityManager.createQuery(cq)
                .setFirstResult((int) ((page-1)*30))
                .setMaxResults((int) (page * 30))
                .getResultList();
    }

    @Override
    @Transactional
    public void deleteFilmById(Long id) {
        userFilmRepository.deleteByFilmId(id);
        filmRepository.deleteById(id);
    }

    @Override
    public void saveFilmGenre(FilmGenreCreateDto filmGenreCreateDto) {
        filmGenreRepository.save(new FilmGenre(filmGenreCreateDto.getFilmId(), filmGenreCreateDto.getGenreId()));
    }

    @Override
    public void saveUserFilm(UserFilmCreateDto userFilmCreateDto) {
        userFilmRepository.save(new UserFilm(userFilmCreateDto.getUserId(), userFilmCreateDto.getFilmId(), userFilmCreateDto.getRating()));
    }

    @Override
    public void updateUserFilm(UserFilmUpdateDto userFilmUpdateDto) {
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
        userFilmRepository.deleteById(id);
    }

    @Override
    public void deleteFilmGenreById(Long id) {
        filmGenreRepository.deleteById(id);
    }
}
