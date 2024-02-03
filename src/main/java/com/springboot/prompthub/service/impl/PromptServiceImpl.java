package com.springboot.prompthub.service.impl;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.models.entity.Project;
import com.springboot.prompthub.models.entity.Prompt;
import com.springboot.prompthub.models.entity.User;
import com.springboot.prompthub.models.request.CreatePromptRequest;
import com.springboot.prompthub.models.request.UpdatePromptRequest;
import com.springboot.prompthub.repository.ProjectRepository;
import com.springboot.prompthub.repository.PromptRepository;
import com.springboot.prompthub.service.ProjectService;
import com.springboot.prompthub.service.PromptService;
import com.springboot.prompthub.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptServiceImpl implements PromptService {
    private final ProjectService projectService;
    private final PromptRepository promptRepository;
    private final AuthenticationService authenticationService;

    public PromptServiceImpl(
            ProjectService projectService,
            PromptRepository promptRepository,
            AuthenticationService authenticationService) {

        this.projectService = projectService;
        this.promptRepository = promptRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<Prompt> getAllPrompts(String projectId) {
        User user = authenticationService.getAuthenticatedUser();
        Project project = projectService.getProject(projectId);

        return promptRepository.findAllByCreatedByAndProject(user, project);
    }

    @Override
    public Prompt getPrompt(String projectId, String promptId) {
        User user = authenticationService.getAuthenticatedUser();
        Project project = projectService.getProject(projectId);

        return promptRepository.findByCreatedByAndProjectAndId(user,
                project,
                promptId)
                .orElseThrow(() -> new APIException(
                        HttpStatus.NOT_FOUND,
                        AppConstant.ERROR_API_PROMPT_NOT_FOUND));
    }

    @Override
    public void createPrompt(CreatePromptRequest request, String projectId) {
        Project project = projectService.getProject(projectId);

        Prompt prompt = new Prompt();
        prompt.setName(request.getName());
        prompt.setDescription(request.getDescription());

        promptRepository.save(prompt);
    }

    @Override
    public void updatePrompt(
            UpdatePromptRequest request,
            String projectId,
            String promptId) {

        Project project = projectService.getProject(projectId);
        Prompt prompt = getPrompt(projectId, promptId);

        prompt.setName(request.getName());
        prompt.setDescription(request.getDescription());
        prompt.setModel(request.getModel());
        prompt.setTokens(request.getTokens());
        prompt.setMessages(request.getMessages());

        promptRepository.save(prompt);
    }

    @Override
    public void deletePrompt(String projectId, String promptId) {
        Project project = projectService.getProject(projectId);
        Prompt prompt = getPrompt(projectId, promptId);

        promptRepository.save(prompt);
    }
}
