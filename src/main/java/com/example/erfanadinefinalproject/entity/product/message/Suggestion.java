package com.example.erfanadinefinalproject.entity.product.message;


import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.user.Worker;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Suggestion extends BaseMessage {


    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "order_ID_mo")
    protected MainOrder order;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "worker_ID_mo")
    private Worker worker;

    private Date duration;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private SuggestionStatus suggestionStatus = SuggestionStatus.PENDING;

    private BigDecimal SuggestionPrice;
}
