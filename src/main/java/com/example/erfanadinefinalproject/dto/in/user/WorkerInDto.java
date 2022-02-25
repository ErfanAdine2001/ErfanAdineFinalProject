package com.example.erfanadinefinalproject.dto.in.user;


import com.example.erfanadinefinalproject.dto.core.BasePersonDto;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.work.MainService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Lob;
import java.math.BigDecimal;
import java.util.Set;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerInDto extends BasePersonDto {

    private MainOrder order;

    @Lob
    private byte[] image;


    Set<MainService> mainServiceList;

    private BigDecimal accountBalance;


    private BigDecimal debtToTheCompany;

}
