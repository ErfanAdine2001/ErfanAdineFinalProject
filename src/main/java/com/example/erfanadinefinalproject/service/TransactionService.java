package com.example.erfanadinefinalproject.service;


import com.example.erfanadinefinalproject.entity.Transaction;


import java.math.BigDecimal;
import java.util.List;


public interface TransactionService {

    public Transaction save(Transaction entity);

    public Transaction findById(Long id);

    public List<Transaction> findAll();

    public void updateAmountOfTransaction(Long id, BigDecimal amount);

    public void delete(Long id);

    public List<Transaction>  findAllByUserId(Long id);
}