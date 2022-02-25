package com.example.erfanadinefinalproject.service;


import com.example.erfanadinefinalproject.dto.in.product.MainOrderInDto;
import com.example.erfanadinefinalproject.dto.out.product.MainOrderOutDto;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.OrderStatus;


import java.util.List;


public interface OrderService {
    public MainOrderOutDto save(MainOrderInDto entity);

    public MainOrderOutDto save(MainOrder entity);


    public List<MainOrder> findAll();


    public MainOrder findById(Long id);


    public void delete(Long id);

    public void update(Long id, String address);

    public List<MainOrder> findAllOrderByStatusWateForSuggestion(OrderStatus status);

    public List<MainOrder> findAllOrderByStatusOfStatus(OrderStatus status);
}
