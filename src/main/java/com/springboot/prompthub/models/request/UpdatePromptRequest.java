package com.springboot.prompthub.models.request;

import com.springboot.prompthub.models.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePromptRequest {
    private String name;
    private String description;
    private String model;
    private int tokens;
    private List<Message> messages;
}
