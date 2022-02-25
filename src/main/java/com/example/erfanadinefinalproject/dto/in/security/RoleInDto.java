package com.example.erfanadinefinalproject.dto.in.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleInDto {


    private String roleName;

    private Set<String> permissionsName;

//    private Admin admin;

}
