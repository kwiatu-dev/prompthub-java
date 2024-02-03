package com.springboot.prompthub.service;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.models.entity.Project;
import com.springboot.prompthub.models.entity.User;
import com.springboot.prompthub.models.request.CreateProjectRequest;
import com.springboot.prompthub.models.request.UpdateProjectRequest;
import com.springboot.prompthub.utils.AppConstant;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Project getProject(String projectId);
    void createProject(CreateProjectRequest request);
    void updateProject(UpdateProjectRequest request, String projectId);
    void deleteProject(String projectId);
}
