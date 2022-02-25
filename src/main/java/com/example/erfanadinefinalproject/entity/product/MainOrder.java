package com.example.erfanadinefinalproject.entity.product;


import com.example.erfanadinefinalproject.entity.Transaction;
import com.example.erfanadinefinalproject.entity.core.BaseEntity;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.user.Admin;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.work.SubService;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
public class MainOrder extends BaseEntity {
    @Column
    private String addres;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_ID_mm_l")
    private List<Admin> admin;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_ID_mo")
    private User user;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "suggestion_ID_oo")
    private Suggestion suggestion;


    @OneToOne
    @JoinColumn(name = "sub_service_id")
    private SubService subService;


    @LastModifiedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date timeStartWork;

    @LastModifiedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date timefinishedWork;


    @OneToOne
    @JoinColumn(name = "transaction_id_oo")
    private Transaction transaction ;




}

