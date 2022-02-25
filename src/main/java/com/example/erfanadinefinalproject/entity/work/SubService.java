package com.example.erfanadinefinalproject.entity.work;
import com.example.erfanadinefinalproject.entity.core.BaseEntity;
import com.example.erfanadinefinalproject.entity.product.message.Request;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class SubService extends BaseEntity {

    @Column
    private Integer number;



//    @OneToOne(cascade = CascadeType.ALL,mappedBy = "subService")
//    private MainServiceInDto mainService;
    private String name;

    private BigDecimal basePrice;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mainService_ID_mm")
    private List<MainService> mainService;

    @OneToOne(cascade = CascadeType.ALL)
    private Request request;

}

