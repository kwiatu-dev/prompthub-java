package com.springboot.prompthub.models.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    public String role;
    public String content;
}
