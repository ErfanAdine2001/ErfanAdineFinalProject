package com.example.erfanadinefinalproject.dto.in.user;

import com.example.erfanadinefinalproject.entity.product.OrderStatus;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.user.Admin;
import com.example.erfanadinefinalproject.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  ShowAllOrdersByUserIdInDto {

    private String addres;
    private OrderStatus status;
    private List<Admin> admin;
    private User user;
    private Suggestion suggestion;

    Integer PageNumber;
    Integer PageSize;
}
