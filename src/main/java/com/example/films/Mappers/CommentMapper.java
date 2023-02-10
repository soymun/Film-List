package com.example.films.Mappers;

import com.example.films.dto.CommentCreateDto;
import com.example.films.dto.CommentDto;
import com.example.films.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment commentCreateDtoToComment(CommentCreateDto commentCreateDto);

    CommentDto commentToCommentDto(Comment comment);
}
