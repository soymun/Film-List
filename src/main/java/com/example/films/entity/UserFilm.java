package com.example.films.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFilm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", insertable = false, updatable = false)
    private Film film;

    @Column(name = "film_id")
    private Long filmId;

    private Long placeInRatingUser;

    public UserFilm(Long userId, Long filmId, Long placeInRatingUser) {
        this.userId = userId;
        this.filmId = filmId;
        this.placeInRatingUser = placeInRatingUser;
    }
}
