package com.springboot.prompthub.entity;

import com.springboot.prompthub.converter.SoftDeleteTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity{
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
}
