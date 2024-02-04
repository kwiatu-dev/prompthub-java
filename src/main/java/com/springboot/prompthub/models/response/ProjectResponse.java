package com.springboot.prompthub.models.response;

import java.util.List;


public record ProjectResponse (
        String id,
        String name,
        String description,
        List<PromptResponse> prompts,
        int promptsCount,
        String createdAt,
        String updatedAt,
        String deletedAt) { }
