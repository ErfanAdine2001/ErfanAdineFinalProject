package com.example.erfanadinefinalproject.entity.work;


import com.example.erfanadinefinalproject.entity.core.BaseEntity;
import com.example.erfanadinefinalproject.entity.user.Worker;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MainService extends BaseEntity {


    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_ID_mm")
    private Set<Worker> worker;
    private  String description;

}
