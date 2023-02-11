package com.example.films.Controller;

import com.example.films.Response.ResponseDto;
import com.example.films.dto.CommentCreateDto;
import com.example.films.dto.CommentDto;
import com.example.films.dto.CommentUpdateDto;
import com.example.films.service.Inerface.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/film/comments")
@CrossOrigin(origins="http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentCreateDto commentCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentCreateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/film/{id}")
    public ResponseEntity<?> getCommentByFilmId(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.builder().data(commentService.getCommentByFilmId(id)).build());
    }

    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody CommentUpdateDto commentUpdateDto) {
        return ResponseEntity.ok(ResponseDto.builder().data(commentService.updateComment(commentUpdateDto)).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.builder().data(commentService.getCommentById(id)).build());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getCommentByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.builder().data(commentService.getCommentByUserId(id)).build());
    }
}
