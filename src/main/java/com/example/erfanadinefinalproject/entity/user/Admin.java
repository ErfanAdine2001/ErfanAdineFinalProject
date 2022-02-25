package com.example.erfanadinefinalproject.entity.user;


import com.example.erfanadinefinalproject.entity.core.BasePerson;
import com.example.erfanadinefinalproject.entity.security.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class Admin extends BasePerson {

    private String username;

    private Boolean isEnable;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

}
