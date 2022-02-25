package com.example.erfanadinefinalproject.dto.out.product;

import com.example.erfanadinefinalproject.dto.out.user.UserOutDto;
import com.example.erfanadinefinalproject.dto.out.work.SubServiceOutDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentOutDto {
    private Long id;

    private UserOutDto user;

    private SubServiceOutDto subService;

    private MainOrderOutDto order;
}
