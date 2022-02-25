package com.example.erfanadinefinalproject.service;


import com.example.erfanadinefinalproject.dto.in.work.SubServiceInDto;
import com.example.erfanadinefinalproject.dto.out.work.SubServiceOutDto;
import com.example.erfanadinefinalproject.entity.work.SubService;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface SubService_Service {

    public List<SubService> showSubServices();

    public SubServiceOutDto save(SubServiceInDto subService);

    public SubService save(SubService subService);

    public SubService findById(Long id);

    public List<SubService> findAll();

    public void delete(Long id);
}
