package com.example.films.service.Inerface;

import com.example.films.dto.CommentCreateDto;
import com.example.films.dto.CommentDto;
import com.example.films.dto.CommentUpdateDto;
import com.example.films.entity.Comment;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentCreateDto commentCreateDto);

    void deleteCommentById(Long id);

    List<Comment> getCommentByFilmId(Long id);

    CommentDto updateComment(CommentUpdateDto commentUpdateDto);

    CommentDto getCommentById(Long id);
}
