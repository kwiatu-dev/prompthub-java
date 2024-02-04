package com.springboot.prompthub.service.impl;

import com.springboot.prompthub.models.entity.Project;
import com.springboot.prompthub.models.entity.Prompt;
import com.springboot.prompthub.models.response.ProjectResponse;
import com.springboot.prompthub.models.response.PromptResponse;
import com.springboot.prompthub.utils.AppConstant;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseGeneratorService {
    public ProjectResponse mapToDTO(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getPrompts() != null ? mapToDTO(project.getPrompts(), new Prompt()) : null,
                project.getPrompts() != null ? project.getPrompts().size() : 0,
                project.getCreatedAt() != null ? new SimpleDateFormat(AppConstant.DEFAULT_DATE_FORMAT).format(project.getCreatedAt()) : "",
                project.getModifiedAt() != null ? new SimpleDateFormat(AppConstant.DEFAULT_DATE_FORMAT).format(project.getModifiedAt()) : "",
                project.getDeletedAt() != null ? new SimpleDateFormat(AppConstant.DEFAULT_DATE_FORMAT).format(project.getDeletedAt()) : "");
    }

    public PromptResponse mapToDTO(Prompt prompt) {
        return new PromptResponse(
                prompt.getId(),
                prompt.getProject().getId(),
                prompt.getName(),
                prompt.getDescription(),
                prompt.getModel(),
                prompt.getTokens(),
                prompt.getMessages(),
                prompt.getMessages() != null ? prompt.getMessages().size() : 0,
                prompt.getCreatedAt() != null ? new SimpleDateFormat(AppConstant.DEFAULT_DATE_FORMAT).format(prompt.getCreatedAt()) : "",
                prompt.getModifiedAt() != null ? new SimpleDateFormat(AppConstant.DEFAULT_DATE_FORMAT).format(prompt.getModifiedAt()) : "",
                prompt.getDeletedAt() != null ? new SimpleDateFormat(AppConstant.DEFAULT_DATE_FORMAT).format(prompt.getDeletedAt()) : "");
    }

    public List<PromptResponse> mapToDTO(List<Prompt> prompts, Prompt instance) {
        return prompts.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ProjectResponse> mapToDTO(List<Project> projects, Project instance) {
        return projects.stream()
                .map(this::mapToDTO)
                .toList();
    }
}
