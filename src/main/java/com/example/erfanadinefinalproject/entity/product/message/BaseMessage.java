package com.example.erfanadinefinalproject.entity.product.message;


import com.example.erfanadinefinalproject.entity.core.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseMessage extends BaseEntity {


//    @Column(nullable = false)
    private BigDecimal price;


//    @Column(nullable = false)
    private Date suggestedDateTime;

    private String details;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private BaseMessageStatus status = BaseMessageStatus.WAITING;
}
