package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.dto.in.product.message.RequestInDto;
import com.example.erfanadinefinalproject.dto.out.product.message.RequestOutDto;
import com.example.erfanadinefinalproject.entity.product.message.Request;
import com.example.erfanadinefinalproject.repo.RequestRepository;
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
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;


    @Override
    public RequestOutDto save(RequestInDto entity) {
        Request request = new Request();
        request.setSubService(entity.getSubService());
        request.setAddress(entity.getAddress());
        request.setOrder(entity.getOrder());
        request.setDetails(entity.getDetails());
        request.setUpdatedTime(new Date());
        request.setCreatedTime(new Date());
        request.setPrice(entity.getPrice());

        Request result = requestRepository.save(request);

        RequestOutDto requestOutDto = new RequestOutDto();
        requestOutDto.setId(result.getId());

        return requestOutDto;
    }

    @Override
    public Request findById(Long id) {
        return requestRepository.findById(id).get();
    }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        requestRepository.delete(findById(id));
    }
}
