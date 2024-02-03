package com.springboot.prompthub.controller;

import com.springboot.prompthub.models.entity.Prompt;
import com.springboot.prompthub.models.request.CreatePromptRequest;
import com.springboot.prompthub.models.request.UpdatePromptRequest;
import com.springboot.prompthub.models.response.SuccessResponse;
import com.springboot.prompthub.service.PromptService;
import com.springboot.prompthub.utils.AppConstant;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.API_VERSION + "projects/{projectId}")
public class PromptController {
    private final PromptService promptService;

    public PromptController(PromptService promptService) {
        this.promptService = promptService;
    }

    @GetMapping("prompts")
    public ResponseEntity<List<Prompt>> getAllPrompts(
            @PathVariable("projectId") String projectId) {

        return new ResponseEntity<>(
                promptService.getAllPrompts(projectId),
                HttpStatus.OK);
    }

    @GetMapping("prompts/{promptId}")
    public ResponseEntity<Prompt> getPrompt(
            @PathVariable("projectId") String projectId,
            @PathVariable("promptId") String promptId) {

        return new ResponseEntity<>(
                promptService.getPrompt(projectId, promptId),
                HttpStatus.OK);
    }

    @PostMapping("prompts")
    public ResponseEntity<SuccessResponse> createPrompt(
            @RequestBody CreatePromptRequest request,
            @PathVariable("projectId") String projectId) {

        promptService.createPrompt(request, projectId);

        return new ResponseEntity<>(
                new SuccessResponse(),
                HttpStatus.OK);
    }

    @PostMapping("prompts/{promptId}")
    public ResponseEntity<SuccessResponse> updatePrompt(
            UpdatePromptRequest request,
            @PathVariable("projectId") String projectId,
            @PathVariable("promptId") String promptId) {

        promptService.updatePrompt(request, projectId, promptId);

        return new ResponseEntity<>(
                new SuccessResponse(),
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
