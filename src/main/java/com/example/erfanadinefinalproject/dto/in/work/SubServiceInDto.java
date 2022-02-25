package com.example.erfanadinefinalproject.dto.in.work;

import com.example.erfanadinefinalproject.dto.core.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubServiceInDto extends BaseEntityDto {


    private String name;

    private BigDecimal basePrice;

    private List<Long> mainServiceId;

}
