package com.springboot.prompthub.models.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", nullable = false, length = 256)
    private String description;

    @OneToMany(mappedBy = "project")
    @Column(name = "prompts", nullable = true)
    private List<Prompt> prompts;
}
