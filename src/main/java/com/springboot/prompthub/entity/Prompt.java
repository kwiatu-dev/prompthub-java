package com.springboot.prompthub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Prompt extends BaseEntity{
    @ManyToOne
    @Column(name = "project", nullable = false)
    private Project project;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", nullable = false, length = 256)
    private String description;

    @Column(nullable = false, length = 64)
    private String model;

    @Column(nullable = false)
    private int tokens;

    //dodaj pole messages jako json
}
