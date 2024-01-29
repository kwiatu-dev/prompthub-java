package com.springboot.prompthub.data;

import com.springboot.prompthub.constant.Constant;
import com.springboot.prompthub.entity.BaseEntity;
import com.springboot.prompthub.entity.Project;
import com.springboot.prompthub.entity.Prompt;
import com.springboot.prompthub.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder {
    private final DataFactory dataFactory;

    public DatabaseSeeder(DataFactory dataFactory){
        this.dataFactory = dataFactory;
    }

    public SeedResult seed(){
        Random random = new Random();

        User admin = new User();
        admin.setEmail(Constant.DEFAULT_ADMIN_EMAIL);
        admin.setPassword(Constant.DEFAULT_ADMIN_PASSWORD);

        List<User> users = dataFactory.generateUsers(10);
        users.forEach(user -> setAuditableBy(user, admin));

        List<Project> projects = dataFactory.generateProjects(10);

        projects.forEach(project -> {
            User user = users.get(random.nextInt(users.size()));
            setAuditableBy(project, user);
        });

        List<Prompt> prompts = dataFactory.generatePrompts(30);

        prompts.forEach(prompt -> {
            Project project = projects.get(random.nextInt(projects.size()));
            prompt.setProject(project);
            setAuditableBy(prompt, project.getCreatedBy());
        });

        return new SeedResult(
            users,
            projects,
            prompts
        );
    }

    private void setAuditableBy(Object object, User auditableBy){
        if (object instanceof BaseEntity entity) {
            entity.setCreatedBy(auditableBy);

            if(entity.getModifiedAt() != null){
                entity.setModifiedBy(auditableBy);
            }

            if(entity.getDeletedAt() != null){
                entity.setDeletedBy(auditableBy);
            }
        }
    }
}
