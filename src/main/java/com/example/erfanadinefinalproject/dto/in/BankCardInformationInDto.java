package com.example.erfanadinefinalproject.dto.in;

import lombok.*;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BankCardInformationInDto {

    //TODO @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ new dto
    @Pattern(regexp = "\\W*\\d{8,17}\\b")
    private  Integer accountNumber ;

    private  BigDecimal balance= new  BigDecimal(1000000);


//
//    public Boolean checkAccountNumber(){
//        if ()
//    }
}
