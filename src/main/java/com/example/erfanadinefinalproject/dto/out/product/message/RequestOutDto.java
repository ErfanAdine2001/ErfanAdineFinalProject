package com.example.erfanadinefinalproject.dto.out.product.message;


import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.message.SuggestionStatus;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.user.Worker;
import com.example.erfanadinefinalproject.entity.work.SubService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class RequestOutDto {

    private Long id;

    private String address;

    protected MainOrder order;

    private Worker worker;

    private Double duration;

    @Enumerated(EnumType.STRING)
    private SuggestionStatus suggestionStatus ;

    private SubService subService;

    private User user;
}
