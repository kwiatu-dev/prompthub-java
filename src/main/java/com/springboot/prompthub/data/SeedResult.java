package com.springboot.prompthub.data;

import com.springboot.prompthub.entity.Project;
import com.springboot.prompthub.entity.Prompt;
import com.springboot.prompthub.entity.User;

import java.util.List;

public class SeedResult{
    public List<User> users;
    public List<Project> projects;
    public List<Prompt> prompts;

    public SeedResult(List<User> users, List<Project> projects, List<Prompt> prompts) {
        this.users = users;
        this.projects = projects;
        this.prompts = prompts;
    }
}
