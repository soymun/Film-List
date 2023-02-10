package com.example.films.service.Impl;

import com.example.films.Exceptions.NoFindResource;
import com.example.films.Mappers.CommentMapper;
import com.example.films.dto.CommentCreateDto;
import com.example.films.dto.CommentDto;
import com.example.films.dto.CommentUpdateDto;
import com.example.films.entity.Comment;
import com.example.films.repository.CommentRepository;
import com.example.films.service.Inerface.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentDto createComment(CommentCreateDto commentCreateDto) {
        log.info("Сохранение рецензии пользователя с id {}", commentCreateDto.getUserId());
        return commentMapper.commentToCommentDto(commentRepository.save(commentMapper.commentCreateDtoToComment(commentCreateDto)));
    }

    @Override
    @Transactional
    public void deleteCommentById(Long id) {
        log.info("Удаление комментария с id {}", id);
    }

    @Override
    public List<CommentDto> getCommentByFilmId(Long id) {
        log.info("Выдача коментариев по фильму с id {}", id);
        return commentRepository.getCommentByFilmId(id).stream().map(commentMapper::commentToCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(CommentUpdateDto commentUpdateDto) {
        log.info("Изменение комментария с id {}", commentUpdateDto.getId());
        Comment comment = commentRepository.getCommentById(commentUpdateDto.getId()).orElseThrow(() ->{throw new NoFindResource("Коментарий не найден");});
        if(commentUpdateDto.getComment() != null){
            comment.setComment(comment.getComment());
        }
        if(commentUpdateDto.getLikes() != null){
            comment.setLikes(commentUpdateDto.getLikes());
        }
        if(commentUpdateDto.getGrade() != null){
            comment.setGrade(commentUpdateDto.getGrade());
        }
        if(commentUpdateDto.getDisLikes() != null){
                comment.setDisLikes(commentUpdateDto.getDisLikes());
        }
        return commentMapper.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto getCommentById(Long id) {
        log.info("Выдача комментария по id {}", id);
        return commentMapper.commentToCommentDto(commentRepository.getCommentById(id).orElseThrow(() -> {throw new NoFindResource("Комментарий не найлен");}));
    }

    @Override
    public List<CommentDto> getCommentByUserId(Long id) {
        log.info("Выдача комментарев пользователя с id {}", id);
        return commentRepository.getCommentByUserId(id).stream().map(commentMapper::commentToCommentDto).collect(Collectors.toList());
    }
}
