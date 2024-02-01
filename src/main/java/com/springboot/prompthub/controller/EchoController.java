package com.springboot.prompthub.controller;

import com.springboot.prompthub.models.request.EchoRequest;
import com.springboot.prompthub.models.response.SuccessResponse;
import com.springboot.prompthub.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstant.API_VERSION)
@PreAuthorize("hasRole('USER')")
public class EchoController {
    @PostMapping("echo")
    public ResponseEntity<SuccessResponse> echo(@RequestBody EchoRequest request){
        return new ResponseEntity<>(new SuccessResponse(request.getMessage()), HttpStatus.OK);
    }
}
