package com.example.erfanadinefinalproject.dto.in.work;

import com.example.erfanadinefinalproject.dto.core.BaseEntityDto;
import com.example.erfanadinefinalproject.entity.user.Worker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MainServiceInDto extends BaseEntityDto {

    private String name;
    private Set<Worker> workers;
    private  String description;

}
