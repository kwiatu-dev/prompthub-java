package com.springboot.prompthub.models.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity{
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
