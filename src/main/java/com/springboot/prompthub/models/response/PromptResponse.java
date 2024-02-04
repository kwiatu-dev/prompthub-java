package com.springboot.prompthub.models.response;

import com.springboot.prompthub.models.entity.Message;

import java.util.List;

public record PromptResponse (
    String id,
    String projectId,
    String name,
    String description,
    String model,
    int tokens,
    List<Message> messages,
    int messagesCount,
    String createdAt,
    String updatedAt,
    String deletedAt) { }
