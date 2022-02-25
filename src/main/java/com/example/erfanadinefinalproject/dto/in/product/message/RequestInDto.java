package com.example.erfanadinefinalproject.dto.in.product.message;


import com.example.erfanadinefinalproject.dto.core.BaseMessageDto;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.message.SuggestionStatus;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.user.Worker;
import com.example.erfanadinefinalproject.entity.work.SubService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RequestInDto extends BaseMessageDto {

    private String address;

    protected MainOrder order;

    private Worker worker;

    private Double duration;

    @Enumerated(EnumType.STRING)
    private SuggestionStatus suggestionStatus ;

    private SubService subService;

    private User user;

}
