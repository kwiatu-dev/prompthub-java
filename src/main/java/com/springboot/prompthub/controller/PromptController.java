package com.springboot.prompthub.controller;

import com.springboot.prompthub.models.entity.Project;
import com.springboot.prompthub.models.entity.Prompt;
import com.springboot.prompthub.models.request.CreatePromptRequest;
import com.springboot.prompthub.models.request.UpdatePromptRequest;
import com.springboot.prompthub.models.response.PromptResponse;
import com.springboot.prompthub.models.response.SuccessResponse;
import com.springboot.prompthub.service.PromptService;
import com.springboot.prompthub.service.impl.ResponseGeneratorService;
import com.springboot.prompthub.utils.AppConstant;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.API_VERSION + "/projects/{projectId}")
public class PromptController {
    private final PromptService promptService;
    private final ResponseGeneratorService responseGeneratorService;

    public PromptController(
            PromptService promptService,
            ResponseGeneratorService responseGeneratorService) {

        this.promptService = promptService;
        this.responseGeneratorService = responseGeneratorService;
    }

    @GetMapping("prompts")
    public ResponseEntity<List<PromptResponse>> getAllPrompts(
            @PathVariable("projectId") String projectId) {

        List<Prompt> prompts = promptService.getAllPrompts(projectId);

        return new ResponseEntity<>(
                responseGeneratorService.mapToDTO(prompts, new Prompt()),
                HttpStatus.OK);
    }

    @GetMapping("prompts/{promptId}")
    public ResponseEntity<PromptResponse> getPrompt(
            @PathVariable("projectId") String projectId,
            @PathVariable("promptId") String promptId) {
        Prompt prompt = promptService.getPrompt(projectId, promptId);

        return new ResponseEntity<>(
                responseGeneratorService.mapToDTO(prompt),
                HttpStatus.OK);
    }

    @PostMapping("prompts")
    public ResponseEntity<PromptResponse> createPrompt(
            @RequestBody CreatePromptRequest request,
            @PathVariable("projectId") String projectId) {

        Prompt prompt = promptService.createPrompt(request, projectId);

        return new ResponseEntity<>(
                responseGeneratorService.mapToDTO(prompt),
                HttpStatus.CREATED);
    }

    @PutMapping("prompts/{promptId}")
    public ResponseEntity<PromptResponse> updatePrompt(
            @RequestBody UpdatePromptRequest request,
            @PathVariable("projectId") String projectId,
            @PathVariable("promptId") String promptId) {

        Prompt prompt = promptService.updatePrompt(request, projectId, promptId);

        return new ResponseEntity<>(
                responseGeneratorService.mapToDTO(prompt),
                HttpStatus.OK);
    }

    @DeleteMapping("prompts/{promptId}")
    public ResponseEntity<SuccessResponse> deletePrompt(
            @PathVariable("projectId") String projectId,
            @PathVariable("promptId") String promptId) {

        promptService.deletePrompt(projectId, promptId);

        return new ResponseEntity<>(
                new SuccessResponse(),
                HttpStatus.OK);
    }
}
