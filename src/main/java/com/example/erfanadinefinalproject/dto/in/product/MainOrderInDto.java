package com.example.erfanadinefinalproject.dto.in.product;


import com.example.erfanadinefinalproject.dto.core.BaseEntityDto;
import com.example.erfanadinefinalproject.entity.product.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MainOrderInDto extends BaseEntityDto {

    private String address;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long subServiceId;

    private Long suggestionId;

    private Long userId;

    private BigDecimal suggestionPrice;

    private Long subService;

}
