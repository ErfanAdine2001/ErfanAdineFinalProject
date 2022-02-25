package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.dto.in.work.MainServiceInDto;
import com.example.erfanadinefinalproject.dto.out.work.MainServiceOutDto;
import com.example.erfanadinefinalproject.entity.work.MainService;
import com.example.erfanadinefinalproject.exception.EmailNotValidException;
import com.example.erfanadinefinalproject.exception.NameNotValidException;
import com.example.erfanadinefinalproject.exception.NullFieldException;
import com.example.erfanadinefinalproject.exception.PasswordNotValidException;
import com.example.erfanadinefinalproject.repo.MainServiceRepository;
import com.example.erfanadinefinalproject.repo.WorkerRepository;
import com.example.erfanadinefinalproject.service.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService_ServiceImpl implements MainService_Service {

    private final MainServiceRepository mainService_ServiceImpl;
    private final WorkerRepository workerRepository;
    private Validation validation;

    @Override
    public List<MainService> findAll() {
        return mainService_ServiceImpl.findAll();
    }

    @Override
    public void removeById(Long id) throws NameNotValidException, EmailNotValidException, PasswordNotValidException, NullFieldException {

        if (Validation.checkBaseCustomerIsValid(workerRepository.findById(id).get())) {
            mainService_ServiceImpl.removeById(workerRepository.getById(id).getId());
        }

    }

    @Override
    public void updateById(Long id) throws NameNotValidException, EmailNotValidException, PasswordNotValidException, NullFieldException {
        if (Validation.checkBaseCustomerIsValid(workerRepository.findById(id).get())) {
            mainService_ServiceImpl.removeById(workerRepository.getById(id).getId());
        }
    }

    @Override
    public MainService findByName(String name) {
        return mainService_ServiceImpl.findByName(name);
    }

    @Override
    public MainServiceOutDto save(MainServiceInDto entity) {

        MainService mainService = new MainService();
        mainService.setName(entity.getName());
        mainService.setWorker(entity.getWorkers());
        mainService.setUpdatedTime(new Date());
        mainService.setCreatedTime(new Date());

        mainService_ServiceImpl.save(mainService);

        MainServiceOutDto mainServiceOutDto = new MainServiceOutDto();
        mainServiceOutDto.setId(entity.getId());

        return mainServiceOutDto;

    }

    @Override
    public MainServiceOutDto save(MainService entity) {
        mainService_ServiceImpl.save(entity);
        MainServiceOutDto mainServiceOutDto = new MainServiceOutDto();
        mainServiceOutDto.setId(entity.getId());

        return mainServiceOutDto;

    }

    @Override
    public MainService findById(Long id) {
        return mainService_ServiceImpl.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        mainService_ServiceImpl.delete(findById(id));
    }
}
