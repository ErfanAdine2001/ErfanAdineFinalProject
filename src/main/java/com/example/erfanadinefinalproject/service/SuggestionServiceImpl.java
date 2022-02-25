package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.dto.out.user.WorkerOrUserSerchOutDto;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.product.message.SuggestionStatus;
import com.example.erfanadinefinalproject.repo.SuggestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SuggestionServiceImpl implements SuggestionService {
    private final SuggestionRepository suggestionRepository;

    @Override
    public Suggestion save(Suggestion entity) {
        return suggestionRepository.save(entity);
    }

    @Override
    public Suggestion findById(Long id) {
        return suggestionRepository.findById(id).get();
    }

    @Override
    public List<Suggestion> findAll() {
        return suggestionRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        suggestionRepository.delete(findById(id));
    }

    @Override
    public Page<WorkerOrUserSerchOutDto> findAllByStatusOrder(Pageable pageable, SuggestionStatus status) {
        Page<WorkerOrUserSerchOutDto> allByStatusOrder = suggestionRepository.findAllBystatusOrder(pageable, status);
        return allByStatusOrder;
    }
}
