package com.example.erfanadinefinalproject.entity.core;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseDuty extends BaseEntity{
    @NotNull
    //TODO javax.validation dependency
    private String name;
}
