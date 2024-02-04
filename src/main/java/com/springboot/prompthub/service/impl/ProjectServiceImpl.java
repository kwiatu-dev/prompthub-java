package com.springboot.prompthub.service.impl;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.models.entity.Project;
import com.springboot.prompthub.models.entity.User;
import com.springboot.prompthub.models.request.CreateProjectRequest;
import com.springboot.prompthub.models.request.UpdateProjectRequest;
import com.springboot.prompthub.models.response.ProjectResponse;
import com.springboot.prompthub.repository.ProjectRepository;
import com.springboot.prompthub.repository.UserRepository;
import com.springboot.prompthub.service.ProjectService;
import com.springboot.prompthub.utils.AppConstant;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final AuthenticationService authenticationService;

    public ProjectServiceImpl(
            ProjectRepository projectRepository,
            AuthenticationService authenticationService) {

        this.projectRepository = projectRepository;
        this.authenticationService = authenticationService;
    }

    public List<Project> getAllProjects() {
        User user = authenticationService.getAuthenticatedUser();

        return projectRepository.findAllByCreatedBy(user);
    }

    public Project getProject(String projectId) {
        User user = authenticationService.getAuthenticatedUser();

        return projectRepository.findByCreatedByAndId(user, projectId)
                .orElseThrow(() -> new APIException(
                        HttpStatus.NOT_FOUND,
                        AppConstant.ERROR_API_PROJECT_NOT_FOUND));
    }

    public void createProject(CreateProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());

        projectRepository.save(project);
    }

    public void updateProject(UpdateProjectRequest request, String projectId) {
        Project project = getProject(projectId);
        project.setName(request.getName());
        project.setDescription(request.getDescription());

        projectRepository.save(project);
    }

    public void deleteProject(String projectId) {
        Project project = getProject(projectId);

        projectRepository.softDelete(project);
    }
}
