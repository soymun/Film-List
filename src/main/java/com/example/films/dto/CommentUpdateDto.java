package com.example.films.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDto {

    private Long id;

    private String comment;

    private Double grade;

    private Long likes;

    private Long disLikes;
}
