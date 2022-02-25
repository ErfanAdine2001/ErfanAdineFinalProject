package com.example.erfanadinefinalproject.dto.core;


import com.example.erfanadinefinalproject.entity.product.message.BaseMessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseMessageDto extends BaseEntityDto {


    private BigDecimal price;

    private Date suggestedDateTime;

    private String details;

    private BaseMessageStatus status;
}
