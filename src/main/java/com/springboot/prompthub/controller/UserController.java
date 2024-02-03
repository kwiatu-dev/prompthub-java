package com.springboot.prompthub.controller;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.models.entity.User;
import com.springboot.prompthub.models.response.SuccessResponse;
import com.springboot.prompthub.repository.UserRepository;
import com.springboot.prompthub.utils.AppConstant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstant.API_VERSION + "/users")
public class UserController {
    private final UserRepository userRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public UserController(
            UserRepository userRepository,
            EntityManager entityManager) {

        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @DeleteMapping("remove")
    public ResponseEntity<SuccessResponse> remove(){
        User user = userRepository.findByEmail("test10@gmail.com")
                .orElseThrow(() -> new APIException(
                        HttpStatus.BAD_REQUEST,
                        ""));

        userRepository.softDelete(user);

        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
