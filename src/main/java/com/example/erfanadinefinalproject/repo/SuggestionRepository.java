package com.example.erfanadinefinalproject.repo;

import com.example.erfanadinefinalproject.dto.out.user.WorkerOrUserSerchOutDto;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.product.message.SuggestionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    @Query("select s from Suggestion s where s.suggestionStatus=:status")
    Page<WorkerOrUserSerchOutDto> findAllBystatusOrder(Pageable pageable , SuggestionStatus status);

}
