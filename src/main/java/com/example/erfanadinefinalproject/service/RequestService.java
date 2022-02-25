package com.example.erfanadinefinalproject.service;


import com.example.erfanadinefinalproject.dto.in.product.message.RequestInDto;
import com.example.erfanadinefinalproject.dto.out.product.message.RequestOutDto;
import com.example.erfanadinefinalproject.entity.product.message.Request;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RequestService {

    public RequestOutDto save(RequestInDto entity);
    public Request findById(Long id);
    public List<Request>findAll();
    public void delete(Long id);
}
