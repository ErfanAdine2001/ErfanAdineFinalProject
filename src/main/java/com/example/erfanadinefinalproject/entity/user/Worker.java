package com.example.erfanadinefinalproject.entity.user;


import com.example.erfanadinefinalproject.entity.core.BasePerson;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.security.Role;
import com.example.erfanadinefinalproject.entity.work.MainService;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Worker extends BasePerson {


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_ID_mo")
    private MainOrder order;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mainService_ID_mm_l")
    private Set<MainService> mainService;

    @Lob
    private byte[] image;

    private BigDecimal accountBalance;

    private BigDecimal debtToTheCompany;


    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    private String username;

}
