package com.springboot.prompthub.controller;

import com.springboot.prompthub.data.DataFactory;
import com.springboot.prompthub.data.DatabaseSeeder;
import com.springboot.prompthub.data.SeedResult;
import com.springboot.prompthub.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final DatabaseSeeder databaseSeeder;

    public UserController(DatabaseSeeder databaseSeeder){
        this.databaseSeeder = databaseSeeder;
    }
    @GetMapping
    public SeedResult getAllUsers(){
        return databaseSeeder.seed();
    }
}
