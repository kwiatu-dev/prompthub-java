package com.springboot.prompthub.controller;

import com.springboot.prompthub.models.entity.Project;
import com.springboot.prompthub.models.request.CreateProjectRequest;
import com.springboot.prompthub.models.request.UpdateProjectRequest;
import com.springboot.prompthub.models.response.SuccessResponse;
import com.springboot.prompthub.service.ProjectService;
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

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(
                projectService.getAllProjects(),
                HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> getProject(@PathVariable("id") String projectId) {
        return new ResponseEntity<>(
                projectService.getProject(projectId),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createProject(@RequestBody CreateProjectRequest request) {
        projectService.createProject(request);

        return new ResponseEntity<>(
                new SuccessResponse(),
                HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<SuccessResponse> updateProject(@RequestBody UpdateProjectRequest request, @PathVariable("id") String projectId) {
        projectService.updateProject(request, projectId);

        return new ResponseEntity<>(
                new SuccessResponse(),
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
