package com.example.erfanadinefinalproject.service;


import com.example.erfanadinefinalproject.dto.in.product.CommentInDto;
import com.example.erfanadinefinalproject.dto.out.product.CommentOutDto;
import com.example.erfanadinefinalproject.entity.product.Comment;

import java.util.List;

public interface CommentService {
    public void update(Long id, String Description, Integer point);

    public void delete(Long id);

    public CommentOutDto save(CommentInDto commentInDto);

    public List<Comment> findAll();

    public Comment findById(Long id);

}
