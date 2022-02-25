package com.example.erfanadinefinalproject.dto.out.work;


import com.example.erfanadinefinalproject.entity.product.message.Request;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SubServiceOutDto {
    private Long id;
    private BigDecimal basePrice;
    private String name;
    private Request request;
}
