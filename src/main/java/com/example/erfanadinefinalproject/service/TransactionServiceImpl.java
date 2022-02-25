package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.entity.Transaction;
import com.example.erfanadinefinalproject.repo.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction save(Transaction entity) {
        return transactionRepository.save(entity);
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public void updateAmountOfTransaction(Long id, BigDecimal amount) {
        Transaction t = findById(id);
        t.setAmount(amount);
        transactionRepository.save(t);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.delete(findById(id));
    }

    @Override
    public List<Transaction> findAllByUserId(Long id) {
        List<Transaction> allByUserId = transactionRepository.findAllByUserId(id);
        return allByUserId;
    }
}
