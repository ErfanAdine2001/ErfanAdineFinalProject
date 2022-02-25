package com.example.erfanadinefinalproject.dto.in.user;


import com.example.erfanadinefinalproject.entity.security.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdminInDto {
    private String firstName;

    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{3,}$")
    private String password;

    private Set<Role> role;

    private String userName;

    @Builder.Default
    private Boolean isEnable = true;
//private Integer names;
}
