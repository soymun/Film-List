package com.example.films.service.Impl;

import com.example.films.dto.CommentCreateDto;
import com.example.films.dto.CommentDto;
import com.example.films.dto.CommentUpdateDto;
import com.example.films.entity.Comment;
import com.example.films.service.Inerface.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {


    @Override
    public CommentDto createComment(CommentCreateDto commentCreateDto) {
        return null;
    }

    @Override
    public void deleteCommentById(Long id) {

    }

    @Override
    public List<Comment> getCommentByFilmId(Long id) {
        return null;
    }

    @Override
    public CommentDto updateComment(CommentUpdateDto commentUpdateDto) {
        return null;
    }

    @Override
    public CommentDto getCommentById(Long id) {
        return null;
    }
}
