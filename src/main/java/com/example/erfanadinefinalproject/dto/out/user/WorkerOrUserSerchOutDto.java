package com.example.erfanadinefinalproject.dto.out.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerOrUserSerchOutDto {
    String fName;
    String lName;
    String Password;
    String email;
    Integer PageNumber;
    Integer PageSize;
}
