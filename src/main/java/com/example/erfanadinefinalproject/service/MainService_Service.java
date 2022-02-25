package com.example.erfanadinefinalproject.service;


import com.example.erfanadinefinalproject.dto.in.work.MainServiceInDto;
import com.example.erfanadinefinalproject.dto.out.work.MainServiceOutDto;
import com.example.erfanadinefinalproject.entity.work.MainService;
import com.example.erfanadinefinalproject.exception.EmailNotValidException;
import com.example.erfanadinefinalproject.exception.NameNotValidException;
import com.example.erfanadinefinalproject.exception.NullFieldException;
import com.example.erfanadinefinalproject.exception.PasswordNotValidException;


import java.util.List;

public interface MainService_Service {

    public List<MainService> findAll();

    public void removeById(Long id) throws NameNotValidException, EmailNotValidException, PasswordNotValidException, NullFieldException;

    public void updateById(Long id) throws NameNotValidException, EmailNotValidException, PasswordNotValidException, NullFieldException;

    public MainService findByName(String name);

    public MainServiceOutDto save(MainServiceInDto entity);

    public MainServiceOutDto save(MainService entity);


    public MainService findById(Long id);

    public void delete(Long id);
}
