package com.example.erfanadinefinalproject.service;


import com.example.erfanadinefinalproject.dto.out.user.WorkerOrUserSerchOutDto;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.product.message.SuggestionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;


public interface SuggestionService {
    public Suggestion save(Suggestion entity);
    public Suggestion findById(Long id);
    public List<Suggestion> findAll();
    public void delete(Long id);

    public Page<WorkerOrUserSerchOutDto> findAllByStatusOrder(Pageable pageable, SuggestionStatus status);
}
