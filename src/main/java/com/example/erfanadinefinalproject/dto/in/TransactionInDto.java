package com.example.erfanadinefinalproject.dto.in;


import com.example.erfanadinefinalproject.dto.core.BaseEntityDto;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.work.SubService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInDto extends BaseEntityDto {

    private Long orderId;

    private BigDecimal amount;

    private User payer;

    private SubService recipient;

    private Long workerId;
}
