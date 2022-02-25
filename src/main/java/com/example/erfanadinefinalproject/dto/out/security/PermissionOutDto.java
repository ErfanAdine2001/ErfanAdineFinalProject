package com.example.erfanadinefinalproject.dto.out.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionOutDto {
    private Long id;
    private String permissionName;
}
