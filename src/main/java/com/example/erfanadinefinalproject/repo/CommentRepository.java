package com.example.erfanadinefinalproject.repo;



import com.example.erfanadinefinalproject.entity.product.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select e from Comment e group by e.id")
    List<Comment> GroupById();
}
