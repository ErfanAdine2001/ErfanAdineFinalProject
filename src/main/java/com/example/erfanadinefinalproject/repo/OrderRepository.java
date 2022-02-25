package com.example.erfanadinefinalproject.repo;

import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<MainOrder, Long> {

    @Query("select e from MainOrder e group by e.id")
    List<MainOrder> GroupById();

    List<MainOrder> findAllByStatus(OrderStatus status);
}
