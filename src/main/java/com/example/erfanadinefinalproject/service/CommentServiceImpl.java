package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.dto.in.product.CommentInDto;
import com.example.erfanadinefinalproject.dto.out.product.CommentOutDto;
import com.example.erfanadinefinalproject.entity.product.Comment;
import com.example.erfanadinefinalproject.repo.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl<MainOrderService> implements CommentService {
    private final CommentRepository commentRepository;
    private final OrderServiceImpl mainOrderService;
    private final UserServiceImpl userService;
    private final SubService_ServiceImpl subServiceService;


    @Override
    public void update(Long id, String Description, Integer point) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public CommentOutDto save(CommentInDto commentInDto) {
        return null;
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public Comment findById(Long id) {
        return null;
    }
}
