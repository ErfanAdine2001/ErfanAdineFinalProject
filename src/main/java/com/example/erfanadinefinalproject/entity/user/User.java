package com.example.erfanadinefinalproject.entity.user;


import com.example.erfanadinefinalproject.entity.core.BasePerson;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.security.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

//@MappedSuperclass
@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BasePerson {


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orders_ID_om_l")
    private List<MainOrder> orders;

    @Lob
    private byte[] image;

    private BigDecimal UserAccountBalance;


    private Boolean isEnabled ;


    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    private String username;
}
