package com.example.erfanadinefinalproject.repo;


import com.example.erfanadinefinalproject.entity.work.MainService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainServiceRepository extends JpaRepository<MainService, Long> {

    @Query("select e from MainService e group by e.id")
    List<MainService> GroupById();


    void removeById(Long id);

    MainService findByName(String name);
}
