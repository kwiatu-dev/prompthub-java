package com.springboot.prompthub.controller;

import com.springboot.prompthub.models.entity.Project;
import com.springboot.prompthub.models.request.CreateProjectRequest;
import com.springboot.prompthub.models.request.UpdateProjectRequest;
import com.springboot.prompthub.models.response.ProjectResponse;
import com.springboot.prompthub.models.response.SuccessResponse;
import com.springboot.prompthub.service.ProjectService;
import com.springboot.prompthub.service.impl.ResponseGeneratorService;
import com.springboot.prompthub.utils.AppConstant;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.API_VERSION + "/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ResponseGeneratorService responseGeneratorService;

    public ProjectController(
            ProjectService projectService,
            ResponseGeneratorService responseGeneratorService) {

        this.projectService = projectService;
        this.responseGeneratorService = responseGeneratorService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();

        return new ResponseEntity<>(
                responseGeneratorService.mapToDTO(projects, new Project()),
                HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable("id") String projectId) {
        Project project = projectService.getProject(projectId);

        return new ResponseEntity<>(
                responseGeneratorService.mapToDTO(project),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody CreateProjectRequest request) {
        Project project = projectService.createProject(request);

        return new ResponseEntity<>(
                responseGeneratorService.mapToDTO(project),
                HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProjectResponse> updateProject(@RequestBody UpdateProjectRequest request, @PathVariable("id") String projectId) {
        Project project = projectService.updateProject(request, projectId);

        return new ResponseEntity<>(
                responseGeneratorService.mapToDTO(project),
                HttpStatus.OK);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<SuccessResponse> deleteProject(@PathVariable("id") String projectId) {
        projectService.deleteProject(projectId);

        return new ResponseEntity<>(
                new SuccessResponse(),
                HttpStatus.OK);
    }
}
