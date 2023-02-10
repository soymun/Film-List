package com.example.films.repository;

import com.example.films.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> getCommentById(Long id);

    List<Comment> getCommentByFilmId(Long filmId);

    List<Comment> getCommentByUserId(Long userId);
}
