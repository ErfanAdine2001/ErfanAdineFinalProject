package com.example.erfanadinefinalproject.repo;

import com.example.erfanadinefinalproject.entity.product.message.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select e from Request e group by e.id")
    List<Request> GroupById();

}
