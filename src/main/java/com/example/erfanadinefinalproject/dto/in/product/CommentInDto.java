package com.example.erfanadinefinalproject.dto.in.product;

import com.example.erfanadinefinalproject.dto.core.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommentInDto extends BaseEntityDto {

    private Integer points;
    private String description;
    private Long userId;
    private Long subServiceId;
    private Long MainOrderId;

}
