package com.example.erfanadinefinalproject.repo;

import com.example.erfanadinefinalproject.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByName(String name) ;
}
