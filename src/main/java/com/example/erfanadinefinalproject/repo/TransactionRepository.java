package com.example.erfanadinefinalproject.repo;

import com.example.erfanadinefinalproject.entity.Transaction;
import com.example.erfanadinefinalproject.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select e from Transaction e group by e.id")
    List<Transaction> GroupById();

    @Query("select t from  Transaction t where t.user.id=:userId")
    List<Transaction> findAllByUserId(Long userId);

    Transaction findAllByUser(User user);
}
