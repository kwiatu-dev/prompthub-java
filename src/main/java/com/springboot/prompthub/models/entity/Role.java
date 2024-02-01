package com.springboot.prompthub.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role{
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "name")
    private String name;

    public Role(String name){
        this.name = name;
    }
}
