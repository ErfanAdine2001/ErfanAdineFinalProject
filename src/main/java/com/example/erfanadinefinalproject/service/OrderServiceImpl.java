package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.dto.in.product.MainOrderInDto;
import com.example.erfanadinefinalproject.dto.out.product.MainOrderOutDto;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.OrderStatus;
import com.example.erfanadinefinalproject.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final SubService_ServiceImpl subServiceService;
    private final  SuggestionServiceImpl suggestionService;
    private final UserServiceImpl userService;

    @Override
    public MainOrderOutDto save(MainOrderInDto entity) {
        MainOrder order = new MainOrder();
        order.setStatus(OrderStatus.WAITING_FOR_SUGGESTION);
        order.setSubService(subServiceService.findById(entity.getSubService()));
        order.setSuggestion(suggestionService.findById(entity.getSuggestionId()));
        order.setUpdatedTime(new Date());
        order.setCreatedTime(new Date());
        order.setUser(userService.findById(entity.getUserId()));

        MainOrderOutDto mainOrderOutDto = new MainOrderOutDto();
        mainOrderOutDto.setId(order.getId());

        return mainOrderOutDto;

    }

    @Override
    public MainOrderOutDto save(MainOrder entity) {
        MainOrder order = new MainOrder();
        order.setStatus(OrderStatus.WAITING_FOR_SUGGESTION);
        order.setSubService(entity.getSubService());
        order.setSuggestion(entity.getSuggestion());
        order.setAddres(entity.getAddres());
        order.setUpdatedTime(new Date());
        order.setCreatedTime(new Date());
        order.setUser(entity.getUser());
        order.setSuggestion(entity.getSuggestion());

        orderRepository.save(order);

        MainOrderOutDto mainOrderOutDto = new MainOrderOutDto();
        mainOrderOutDto.setId(order.getId());

        return mainOrderOutDto;
    }

    @Override
    public List<MainOrder> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public MainOrder findById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        orderRepository.delete(findById(id));
    }

    @Override
    public void update(Long id, String address) {
        MainOrder a = findById(id);
        a.setAddres(address);
        orderRepository.save(a);
    }

    @Override
    public List<MainOrder> findAllOrderByStatusWateForSuggestion(OrderStatus status) {
        List<MainOrder> allByStatus = orderRepository.findAllByStatus(status);
        List<MainOrder> finalOrder = new ArrayList<>();
        for (MainOrder order : allByStatus) {

            if (order.getStatus().equals(OrderStatus.WAITING_FOR_SUGGESTION)){

                finalOrder.add(order);

            }
        }
        return finalOrder;
    }

    @Override
    public List<MainOrder> findAllOrderByStatusOfStatus(OrderStatus status) {
        List<MainOrder> allByStatus = orderRepository.findAllByStatus(status);
        List<MainOrder> finalOrder = new ArrayList<>();
        for (MainOrder order : allByStatus) {

            if (order.getStatus().equals(status)){

                finalOrder.add(order);

            }
        }
        return finalOrder;
    }
}
