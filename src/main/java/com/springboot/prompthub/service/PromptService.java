package com.springboot.prompthub.service;

import com.springboot.prompthub.models.entity.Prompt;
import com.springboot.prompthub.models.request.CreatePromptRequest;
import com.springboot.prompthub.models.request.UpdatePromptRequest;

import java.util.List;

public interface PromptService {
    List<Prompt> getAllPrompts(String projectId);

    Prompt getPrompt(String projectId, String promptId);

    Prompt createPrompt(CreatePromptRequest request, String projectId);

    Prompt updatePrompt(
            UpdatePromptRequest request,
            String projectId,
            String promptId);

    void deletePrompt(String projectId, String promptId);
}
