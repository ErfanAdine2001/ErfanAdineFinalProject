package com.example.erfanadinefinalproject.controller;


import com.example.erfanadinefinalproject.dto.in.product.CommentInDto;
import com.example.erfanadinefinalproject.dto.out.product.CommentOutDto;
import com.example.erfanadinefinalproject.entity.product.Comment;
import com.example.erfanadinefinalproject.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentServiceImpl commentService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<CommentOutDto> create(@Valid @RequestBody CommentInDto request){

        CommentOutDto commentOutDto = commentService.save(request);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentOutDto);

    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<List<Comment>> findAll() {
        List<Comment> list = commentService.findAll();


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(list);

    }

    @GetMapping("/findById/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<CommentOutDto> findById(@PathVariable Long commentId) {
        Comment comment = commentService.findById(commentId);

        CommentOutDto commentOutDto = new CommentOutDto();
        commentOutDto.setId(comment.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentOutDto);


    }

    @PutMapping("/update/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<Comment> update(@PathVariable Long commentId, @RequestBody CommentInDto commentInDto) {
        Comment comment = commentService.findById(commentId);

        commentInDto.setDescription(commentInDto.getDescription());
        commentInDto.setPoints(commentInDto.getPoints());
        commentInDto.setUpdatedTime(new Date());
        commentInDto.setUserId(commentInDto.getUserId());
        commentInDto.setMainOrderId(commentInDto.getMainOrderId());


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comment);


    }

    @DeleteMapping("/delete/{commentId}e")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<String> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Deleted");

    }

}
