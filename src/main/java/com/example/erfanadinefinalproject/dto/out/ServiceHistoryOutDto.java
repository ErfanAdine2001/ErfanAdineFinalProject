package com.example.erfanadinefinalproject.dto.out;

import com.example.erfanadinefinalproject.entity.product.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceHistoryOutDto {

    private String address;
    private Date timeFinishedWork;
    private Date timeStartWork;
    private Long transactionId ;
    private Long suggestionId;
    private OrderStatus status;
    private Long userId;
    private List<Long> adminId;
}
