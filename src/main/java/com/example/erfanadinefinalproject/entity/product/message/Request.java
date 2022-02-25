package com.example.erfanadinefinalproject.entity.product.message;



import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.work.SubService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Request extends BaseMessage {


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_ID_oo")
    protected MainOrder order;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_ID_mo")

    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    private SubService subService;

}
